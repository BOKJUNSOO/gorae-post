package com.gorae.gorae_post.kafka.producer.leaderboard.dto;


import com.gorae.gorae_post.domain.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentEvent {
    public static final  String TOPIC = "comment-produce";
    private String commentId;

    public static CommentEvent fromEntity(Comment comment){
        CommentEvent event = new CommentEvent();
        Long id = comment.getId();
        event.setCommentId(String.valueOf(id));
        return event;
    }

}
