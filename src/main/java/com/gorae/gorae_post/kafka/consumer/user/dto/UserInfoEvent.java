package com.gorae.gorae_post.kafka.consumer.user.dto;

import lombok.Getter;
import lombok.Setter;

// posting 발생 이후 user의 메타 정보를 갱신시키기 위한 토픽
// 2단계 구성

@Getter
@Setter
public class UserInfoEvent {
    public static final String TOPIC = "user-info";

    private String userId;

    private String userName;

    private String userImageUrl;
}
