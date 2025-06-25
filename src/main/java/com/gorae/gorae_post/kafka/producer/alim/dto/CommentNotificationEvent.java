package com.gorae.gorae_post.kafka.producer.alim.dto;


import com.gorae.gorae_post.domain.entity.Comment;
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

    public static CommentNotificationEvent fromEntity(Comment comment){
        CommentNotificationEvent event = new CommentNotificationEvent();
        event.setCommentUserId(comment.getUserInfo().getUserId());
        event.setPostUserId(comment.getQuestion().getUserId());
        event.setCommentContent("답변이 등록되었습니다.");
        event.setEventTime(comment.getCreateAt());
        return event;
    }
}
