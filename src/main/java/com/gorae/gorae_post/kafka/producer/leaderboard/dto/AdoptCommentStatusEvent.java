package com.gorae.gorae_post.kafka.producer.leaderboard.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptCommentStatusEvent {
    public static final String TOPIC = "adopt-comment-status";

    private String postUserId;
    private String adopt;
}

