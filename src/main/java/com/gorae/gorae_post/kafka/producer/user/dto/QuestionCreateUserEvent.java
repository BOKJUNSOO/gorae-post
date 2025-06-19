package com.gorae.gorae_post.kafka.producer.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionCreateUserEvent {
    public static final String TOPIC = "question-create-user";

    private String userId;
}
