package com.gorae.gorae_post.kafka.consumer.user.dto;

import lombok.Getter;
import lombok.Setter;


// User 의 정보가 수정됐다면 Post 에서 사용중인 DB 갱신을 위한 토픽
@Getter
@Setter
public class ChangeUserInfoEvent {
    public static final String TOPIC = "change-user-info";

    // id, name 여러가지 바뀌면 각각 메세지 발행?
    // 각각 메세지 발행이 아니라면 한번에 발행 ? -> 배열 타입 (트랜젝션 보장되니 더 좋을듯)
    private String action;

    private String userId;

    private String userName;

    private String userImageUrl;

}

