package com.gorae.gorae_post.service;

import com.gorae.gorae_post.common.exception.NotFound;
import com.gorae.gorae_post.domain.dto.comment.Comment;
import com.gorae.gorae_post.domain.dto.comment.CommentCreateDto;
import com.gorae.gorae_post.domain.dto.comment.CommentUpdateDto;
import com.gorae.gorae_post.domain.dto.question.CommentDto;
import com.gorae.gorae_post.domain.dto.question.Question;
import com.gorae.gorae_post.domain.dto.user.UserInfo;
import com.gorae.gorae_post.domain.repository.CommentRepository;
import com.gorae.gorae_post.domain.repository.QuestionRepository;
import com.gorae.gorae_post.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    // 답변 생성
    @Transactional
    public Long createComment(CommentCreateDto commentCreateDto, String userId, Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new NotFound("존재하지 않거나 삭제된 글입니다.")
        );
//         TODO: 유저 아이디 검증하는 부분 추가
//        UserInfo userInfo = userRepository.findById(userId).orElseThrow(
//                () -> new NotFound("로그인이 필요한 서비스입니다.")
//        );
        Comment create = commentCreateDto.toEntity(userId, question);
        Comment savedComment = commentRepository.save(create);
        return savedComment.getId();
    }

    //    답변 업데이트
    @Transactional
    public Long updateComment(CommentUpdateDto commentUpdateDto, String userId) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentUpdateDto.getCommentId())
                .orElseThrow(() -> new NotFound("답변을 찾을 수 없습니다."));
        if (!comment.getUserId().equals(userId)) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }
        comment.setUpdateAt(LocalDateTime.now());
        comment.setCommentContent(commentUpdateDto.getCommentContent());
        return comment.getId();
    }
}
