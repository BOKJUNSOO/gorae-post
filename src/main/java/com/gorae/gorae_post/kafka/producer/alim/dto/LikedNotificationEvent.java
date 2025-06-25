package com.gorae.gorae_post.kafka.producer.alim.dto;


import com.gorae.gorae_post.domain.entity.Like;
import com.gorae.gorae_post.kafka.producer.leaderboard.dto.LikeCommentStatusEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikedNotificationEvent {
    public static final String TOPIC = "liked-notification";

    private String postId; // 해당 댓글의 글 주인 Id
    private String commentLikeUserId; // 좋아요를 누른사람
    private String commentUserId; // 좋아요를 눌려진 댓글

    public static LikedNotificationEvent fromEntity(Like like){
        LikedNotificationEvent event = new LikedNotificationEvent();
        Long postId = like.getComment().getQuestion().getId();
        event.setPostId(String.valueOf(postId));
        event.setCommentLikeUserId(like.getUserInfo().getUserId());
        event.setCommentUserId(like.getComment().getUserInfo().getUserId());
        return event;
    }
}
