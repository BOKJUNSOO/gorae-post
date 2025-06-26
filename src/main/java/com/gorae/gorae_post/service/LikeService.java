package com.gorae.gorae_post.service;


import com.gorae.gorae_post.common.exception.NotFound;
import com.gorae.gorae_post.domain.entity.Comment;
import com.gorae.gorae_post.domain.entity.Like;
import com.gorae.gorae_post.domain.dto.like.LikeDto;
import com.gorae.gorae_post.domain.entity.UserInfo;
import com.gorae.gorae_post.domain.repository.CommentRepository;
import com.gorae.gorae_post.domain.repository.LikeRepository;
import com.gorae.gorae_post.domain.repository.UserRepository;
import com.gorae.gorae_post.kafka.producer.KafkaMessageProducer;
import com.gorae.gorae_post.kafka.producer.alim.dto.LikedNotificationEvent;
import com.gorae.gorae_post.kafka.producer.leaderboard.dto.LikeCommentStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final KafkaMessageProducer kafkaMessageProducer;

    @Transactional
    public Long like(LikeDto likeDto, String userId) { // userId는 좋아요를 누른 사람

        UserInfo userInfo = userRepository.findById(userId).
                orElseThrow(() -> new NotFound("로그인이 필요한 서비스입니다."));

        Comment comment = commentRepository.findById(likeDto.getCommentId()).
                orElseThrow(() -> new NotFound("이미 삭제된 답변입니다."));


        //      like 조회
        Optional<Like> existLike = likeRepository.findByUserInfoAndComment(userInfo, comment);

//        like가 존재하면 -> likeStatus는 true일 거니까
        if (existLike.isPresent()) {
            Like like = existLike.get();
            return like.getComment().getLikeCount();
        } else {
            Like like = Like.builder().
                    userInfo(userInfo).
                    comment(comment).
                    likeStatus(true).
                    build();
            likeRepository.save(like);
            comment.increaseLikeCount();
//          좋아요 카프카 이벤트
            Like likeEntity = Like.builder()
                    .userInfo(comment.getUserInfo())
                    .comment(comment)
                    .likeStatus(true)
                    .build();

            LikeCommentStatusEvent likeEvent = LikeCommentStatusEvent.fromEntityLike(likeEntity);
            kafkaMessageProducer.send("like-comment-status", likeEvent);
            return like.getComment().getId();
        }
    }
}
