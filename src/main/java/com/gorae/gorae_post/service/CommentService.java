package com.gorae.gorae_post.service;

import com.gorae.gorae_post.common.exception.BadParameter;
import com.gorae.gorae_post.common.exception.NotFound;
import com.gorae.gorae_post.domain.dto.comment.*;
import com.gorae.gorae_post.domain.dto.question.CommentDto;
import com.gorae.gorae_post.domain.dto.question.Question;
import com.gorae.gorae_post.domain.dto.user.UserInfo;
import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import com.gorae.gorae_post.domain.repository.CommentRepository;
import com.gorae.gorae_post.domain.repository.QuestionRepository;
import com.gorae.gorae_post.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public PageResponseDto<CommentDto> commentView(Long questionId, Pageable pageable) {
        Page<Comment> commentPage = commentRepository.findByQuestionIdWithUser(questionId, pageable);
        List<CommentDto> dtoList = commentPage.getContent().stream()
                .map(CommentDto::fromEntity) // DTO 변환
                .collect(Collectors.toList());
        return PageResponseDto.<CommentDto>builder()
                .content(dtoList)
                .pageNumber(commentPage.getNumber())
                .pageSize(commentPage.getSize())
                .totalPages(commentPage.getTotalPages())
                .totalElements(commentPage.getTotalElements())
                .build();
    }

    //    답변 list 및 페이징
//    private List<CommentDto> readComment(Question question) {
//        List<Comment> commentList = question.getCommentList();
//        List<CommentDto> commentDtoList = new ArrayList<>();
//        for (Comment comment : commentList) {
//            String userId = comment.getUserInfo().getUserId();
//            UserInfo userInfo = userRepository.findById(userId)
//                    .orElseThrow(() -> new NotFound("사용자가 없습니다."));
//            UserInfoDto userInfoDto = UserInfoDto.builder()
//                    .userId(userInfo.getUserId())
//                    .userName(userInfo.getUserName())
//                    .userBadge(userInfo.getUserBadge())
//                    .profileImgUrl(userInfo.getProfileImgUrl())
//                    .build();
//            CommentDto dto = CommentDto.builder()
//                    .commentId(comment.getId())
//                    .commentContent(comment.getCommentContent())
//                    .updateAt(comment.getUpdateAt())
//                    .likeCount(comment.getLikeCount())
//                    .adopt(comment.isAdopt())
//                    .userInfoDto(userInfoDto)
//                    .build();
//            commentDtoList.add(dto);
//        }
//        return commentDtoList;
//    }

    // 답변 생성
    @Transactional
    public Long createComment(CommentCreateDto commentCreateDto, String userId, Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new NotFound("존재하지 않거나 삭제된 글입니다.")
        );
//         TODO: 유저 아이디 검증하는 부분 추가
        UserInfo userInfo = userRepository.findById(userId).orElseThrow(
                () -> new NotFound("로그인이 필요한 서비스입니다.")
        );
        Comment create = commentCreateDto.toEntity(userInfo, question);
        Comment savedComment = commentRepository.save(create);
        return savedComment.getId();
    }

    //    답변 업데이트
    @Transactional
    public Long updateComment(CommentUpdateDto commentUpdateDto, String userId) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentUpdateDto.getCommentId())
                .orElseThrow(() -> new NotFound("답변을 찾을 수 없습니다."));
        if (!comment.getUserInfo().getUserId().equals(userId)) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }
        comment.setUpdateAt(LocalDateTime.now());
        comment.setCommentContent(commentUpdateDto.getCommentContent());
        return comment.getId();
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
            throw new AccessDeniedException("질문 작성자만 채택할 수 있습니다.");
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
        return comment.getId();
    }
}
