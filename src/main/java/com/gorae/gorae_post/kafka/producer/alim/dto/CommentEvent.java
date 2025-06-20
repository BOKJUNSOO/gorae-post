package com.gorae.gorae_post.kafka.producer.alim.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentEvent {
    public static final String TOPIC = "comment";

    private String commentUserId;
    private String postUserId;
    private String commentContent;
    private LocalDateTime eventTime;

}
