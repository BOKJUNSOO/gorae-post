package com.gorae.gorae_post.kafka.producer.alim.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptCommentEvent {
    public static final String TOPIC = "adopt-comment";

    private String postUserId;
    private String adopt;
}
