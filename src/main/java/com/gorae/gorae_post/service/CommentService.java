package com.gorae.gorae_post.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorae.gorae_post.common.exception.BadParameter;
import com.gorae.gorae_post.common.exception.NotFound;
import com.gorae.gorae_post.domain.dto.comment.*;
import com.gorae.gorae_post.domain.dto.question.CommentDto;
import com.gorae.gorae_post.domain.entity.Comment;
import com.gorae.gorae_post.domain.entity.Question;
import com.gorae.gorae_post.domain.entity.UserInfo;
import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import com.gorae.gorae_post.domain.repository.CommentRepository;
import com.gorae.gorae_post.domain.repository.QuestionRepository;
import com.gorae.gorae_post.domain.repository.UserRepository;
import com.gorae.gorae_post.kafka.producer.KafkaMessageProducer;
import com.gorae.gorae_post.kafka.producer.alim.dto.AdoptNotificationEvent;
import com.gorae.gorae_post.kafka.producer.alim.dto.CommentNotificationEvent;
import com.gorae.gorae_post.kafka.producer.leaderboard.dto.AdoptCommentStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final KafkaMessageProducer kafkaMessageProducer;

    @Transactional
    public Map<String,Object> mapCommentContent(String commentContentJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(commentContentJson, new TypeReference<>() {});
    }

    @Transactional(readOnly = true)
    public PageResponseDto<CommentDto> commentView(Long questionId, Pageable pageable, String userId) {
        Sort fixedSort = Sort.by(Sort.Direction.DESC, "adopt")
                .and(Sort.by(Sort.Direction.ASC, "createAt"));
        Pageable finalPageable = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize(), fixedSort);
        Page<Comment> commentPage = commentRepository.findByQuestionIdWithUser(questionId, finalPageable);
        List<CommentDto> dtoList = commentPage.getContent().stream()
                .map(comment ->{
                    try {
                        boolean isAuthor = false;
                        if(userId != null && comment.getUserInfo().getUserId().equals(userId)){
                            isAuthor = true;
                        }
                        return  CommentDto.builder()
                                  .commentId(comment.getId())
                                  .commentContent(mapCommentContent(comment.getCommentContent()))
                                  .likeCount(comment.getLikeCount())
                                  .adopt(comment.isAdopt())
                                  .updateAt(comment.getUpdateAt())
                                  .isAuthor(isAuthor)
                                  .userInfoDto(UserInfoDto.fromEntity(comment.getUserInfo()))
                                  .build();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
        return new PageResponseDto<>(commentPage, dtoList);
    }
    // 답변 생성
    @Transactional
    public CommentCreateDto createComment(CommentCreateDto commentCreateDto, String userId) throws JsonProcessingException {
        Question question = questionRepository.findById(commentCreateDto.getQuestionId()).orElseThrow(
                () -> new NotFound("존재하지 않거나 삭제된 글입니다.")
        );
        UserInfo userInfo = userRepository.findById(userId).orElseThrow(
                () -> new NotFound("로그인이 필요한 서비스입니다.")
        );
        Comment create = commentCreateDto.toEntity(userInfo, question);
        Comment savedComment = commentRepository.save(create);
        UserInfoDto userInfoDto =UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .userName(userInfo.getUserName())
                .profileImgUrl(userInfo.getProfileImgUrl())
                .userBadge(userInfo.getUserBadge())
                .likeBadge(userInfo.getLikeBadge())
                .build();
        CommentNotificationEvent event = CommentNotificationEvent.fromEntity(savedComment);
        kafkaMessageProducer.send("comment-notification", event);
        return CommentCreateDto.builder()
                .questionId(savedComment.getQuestion().getId())
                .commentContent(mapCommentContent(savedComment.getCommentContent()))
                .createAt(savedComment.getCreateAt())
                .adopt(savedComment.isAdopt())
                .likeCount(savedComment.getLikeCount())
                .userInfo(userInfoDto)
                .build();
    }

    //    답변 업데이트
    @Transactional
    public CommentUpdateDto updateComment(CommentUpdateDto commentUpdateDto, String userId) throws AccessDeniedException, JsonProcessingException {
        Comment comment = commentRepository.findById(commentUpdateDto.getCommentId())
                .orElseThrow(() -> new NotFound("답변을 찾을 수 없습니다."));
        Question question = questionRepository.findById(commentUpdateDto.getQuestionId())
                .orElseThrow(() -> new NotFound("게시글이 없습니다."));
        UserInfo userInfo = userRepository.findById(userId).orElseThrow(
                () -> new NotFound("로그인이 필요한 서비스입니다.")
        );
        ObjectMapper mapper = new ObjectMapper();
        if (!comment.getUserInfo().getUserId().equals(userId)) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }
        comment.setCommentContent(mapper.writeValueAsString(commentUpdateDto.getCommentContent()));
        comment.setUpdateAt(LocalDateTime.now());

        UserInfoDto userInfoDto =UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .userName(userInfo.getUserName())
                .profileImgUrl(userInfo.getProfileImgUrl())
                .userBadge(userInfo.getUserBadge())
                .likeBadge(userInfo.getLikeBadge())
                .build();
        return CommentUpdateDto.builder()
                .questionId(comment.getQuestion().getId())
                .commentId(comment.getId())
                .commentContent(mapCommentContent(comment.getCommentContent()))
                .adopt(comment.isAdopt())
                .likeCount(comment.getLikeCount())
                .userInfoDto(userInfoDto)
                .build();
    }

    //    답변 삭제
    @Transactional
    public void deleteComment(Long commentId, String userId) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFound("존재하지 않는 답변입니다."));
        UserInfo userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("인증이 필요한 서비스입니다."));
        if (!comment.getUserInfo().getUserId().equals(userId)) {
            throw new AccessDeniedException("작성자만 삭제 가능합니다.");
        }
        commentRepository.deleteById(commentId);
    }

    //    답변 채택
    @Transactional
    public Long adoptComment(CommentAdoptDto commentAdoptDto, String userId) throws AccessDeniedException {
        Question question = questionRepository.findById(commentAdoptDto.getQuestionId())
                .orElseThrow(() -> new NotFound("존재하지 않거나 삭제된 글입니다."));
        Comment comment = commentRepository.findById(commentAdoptDto.getCommentId())
                .orElseThrow(() -> new NotFound("댓글이 존재하지 않습니다."));
//        작성자가 해당 유저일때 확인
        if (!question.getUserId().equals(userId)) {
            throw new BadParameter("질문 작성자만 채택할 수 있습니다.");
        }
//        답변이 해당 질문에 있는지 확인
        if (!comment.getQuestion().getId().equals(question.getId())) {
            throw new NotFound("해당 질문에 대한 답변이 아닙니다.");
        }
//       이미 채택된 답변이 있는지 확인
        if (commentRepository.getCommentAdoptExisted(commentAdoptDto.getQuestionId()) > 0) {
            throw new BadParameter("이미 채택된 답변이 존재합니다.");
        }
        comment.setAdopt(true);
//        채택 카프카 알림 이벤트
        AdoptNotificationEvent notificationEvent = AdoptNotificationEvent.fromEntity(comment);
        kafkaMessageProducer.send("adopt-notification", notificationEvent);
//        채택 카프카 리더보드 이벤트
        AdoptCommentStatusEvent leaderEvent = AdoptCommentStatusEvent.fromEntity(comment);
        kafkaMessageProducer.send("adopt-comment-status",leaderEvent);
        return comment.getId();
    }
}
