package com.gorae.gorae_post.kafka.producer.leaderboard.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeCommentStatusEvent {
    public static final String TOPIC = "like-comment-status";

    private String postUserId; // 해당 댓글의 글 주인 Id
    private String commentLikeUserId; // 좋아요를 누른사람
    private String commentUserId; // 좋아요를 눌려진 댓글
    private String likeStatus;
}

