package com.gorae.gorae_interaction.kafka.producer.leaderboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChooseEvent {
// 질문 작성자(userId)가 답변(commentId)를 채택한다.
    public static final String TOPIC = "choose";

    private Long userId;
    private Long commentId;
    }
