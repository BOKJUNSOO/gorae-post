package com.gorae.gorae_post.kafka.producer.alim.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptNotificationEvent {
    public static final String TOPIC = "adopt-notification";

    private String postUserId;
    private String adopt;
}
