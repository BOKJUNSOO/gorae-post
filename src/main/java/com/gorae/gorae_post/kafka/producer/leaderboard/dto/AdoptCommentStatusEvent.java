package com.gorae.gorae_post.kafka.producer.leaderboard.dto;


import com.gorae.gorae_post.domain.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptCommentStatusEvent {
    public static final String TOPIC = "adopt-comment-status";

    private String commentId;//채택된 답변
    private String adoptUserId;//채택된 답변의 유저 Id

    public static AdoptCommentStatusEvent fromEntity(Comment comment){
        AdoptCommentStatusEvent event = new AdoptCommentStatusEvent();
        Long adoptId = comment.getId();
        event.setCommentId(String.valueOf(adoptId));
        event.setAdoptUserId(comment.getUserInfo().getUserId());
        return event;
    }
}

