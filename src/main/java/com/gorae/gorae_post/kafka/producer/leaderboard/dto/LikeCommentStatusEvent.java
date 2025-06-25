package com.gorae.gorae_post.kafka.producer.leaderboard.dto;


import com.gorae.gorae_post.domain.entity.Comment;
import com.gorae.gorae_post.domain.entity.Like;
import com.gorae.gorae_post.domain.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeCommentStatusEvent {
    public static final String TOPIC = "like-comment-status";

    private String postId; // 게시글의 유저
    private String commentLikeUserId; // 좋아요를 누른사람
    private String commentUserId; // 좋아요를 눌려진 댓글

    public static LikeCommentStatusEvent fromEntity(Like like){
        LikeCommentStatusEvent event = new LikeCommentStatusEvent();
        Long postId = like.getComment().getQuestion().getId();
        event.setPostId(String.valueOf(postId));
        event.setCommentLikeUserId(like.getUserInfo().getUserId());
        event.setCommentUserId(like.getComment().getUserInfo().getUserId());
        return event;
    }
}

