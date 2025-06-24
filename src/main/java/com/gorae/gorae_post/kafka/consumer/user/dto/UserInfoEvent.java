package com.gorae.gorae_post.kafka.consumer.user.dto;

import lombok.Getter;
import lombok.Setter;

// 유저가 회원가입 했을때 사용되는 TOPIC
@Getter
@Setter
public class UserInfoEvent {
    public static final String TOPIC = "user-info";

    private String userId;

    private String userName;

    private String profileImgUrl;
}
