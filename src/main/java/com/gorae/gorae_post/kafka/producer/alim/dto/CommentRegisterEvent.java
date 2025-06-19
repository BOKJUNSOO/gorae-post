package com.gorae.gorae_post.kafka.producer.alim.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentRegisterEvent {
    public static final String TOPIC = "comment-register";
    
    // 수정인지 새로 작성한것인지
    // register or modify
    private String action;

    private String userId;
    private String postTitle;
    private String comment;

    private LocalDateTime eventTime;

}
