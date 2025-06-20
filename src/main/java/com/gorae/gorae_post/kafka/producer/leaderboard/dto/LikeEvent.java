package com.gorae.gorae_interaction.kafka.producer.leaderboard.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeEvent {
//    사용자(userId)가 질문(commentId)를 좋아요(action)한다.
    public static final String TOPIC = "like";
//    action: "좋아요, 취소"
    private String action;
//    중복 좋아요 방지를 위한 userId
    private Long userId;
    private Long commentId;
}