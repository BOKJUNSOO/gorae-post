package com.gorae.gorae_post.kafka.producer.alim.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentNotificationEvent {
    public static final String TOPIC = "comment-notification";

    private String commentUserId;
    private String postUserId;
    private String commentContent;
    private LocalDateTime eventTime;

}
