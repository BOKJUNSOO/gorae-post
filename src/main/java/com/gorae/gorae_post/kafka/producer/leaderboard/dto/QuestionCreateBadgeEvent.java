package com.gorae.gorae_post.kafka.producer.leaderboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionCreateBadgeEvent {
    public static final String TOPIC = "question-create-badge";

    private String userId;
}
