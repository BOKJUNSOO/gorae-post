package com.gorae.gorae_post.kafka.consumer.leaderboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatusEvent {
    // 질문 상세보기, 답변 에서 보여줄 뱃지
    public static final String TOPIC = "user-status";

    private String userId;

    private String userBadge;
}
