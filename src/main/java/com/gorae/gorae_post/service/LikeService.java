package com.gorae.gorae_post.service;


import com.gorae.gorae_post.common.exception.NotFound;
import com.gorae.gorae_post.domain.dto.comment.Comment;
import com.gorae.gorae_post.domain.dto.like.Like;
import com.gorae.gorae_post.domain.dto.like.LikeDto;
import com.gorae.gorae_post.domain.dto.user.UserInfo;
import com.gorae.gorae_post.domain.repository.CommentRepository;
import com.gorae.gorae_post.domain.repository.LikeRepository;
import com.gorae.gorae_post.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void like(LikeDto likeDto) {

        UserInfo userInfo = userRepository.findById(likeDto.getUserId()).
                orElseThrow(() -> new NotFound("로그인이 필요한 서비스입니다."));

        Comment comment = commentRepository.findById(likeDto.getCommentId()).
                orElseThrow(() -> new NotFound("이미 삭제된 답변입니다."));
//      like 조회
        Optional<Like> existLike = likeRepository.findByUserInfoAndComment(userInfo, comment);

        if (existLike.isPresent()) {
            Like delete = existLike.get();
            likeRepository.deleteById(delete.getId());
            comment.decreaseLikeCount();
        } else {
            Like like = Like.builder().
                    userInfo(userInfo).
                    comment(comment).
                    build();
            likeRepository.save(like);
            comment.increaseLikeCount();
        }
    }
}
