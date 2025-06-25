package com.gorae.gorae_post.kafka.producer.alim.dto;

import com.gorae.gorae_post.domain.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptNotificationEvent {
    public static final String TOPIC = "adopt-notification";

    private String postUserId;
    private String commentUserId;

    public static AdoptNotificationEvent fromEntity(Comment comment){
        AdoptNotificationEvent event = new AdoptNotificationEvent();
        event.setPostUserId(comment.getQuestion().getUserId());
        event.setCommentUserId(comment.getUserInfo().getUserId());
        return event;
    }
}
