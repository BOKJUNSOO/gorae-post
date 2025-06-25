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

    private String commentLikeUserId; // 좋아요를 누른사람
    private String commentId; // 좋아요를 눌려진 댓글
    private String action; // 좋아요 , 취소롤 액션 나눔

    public static LikeCommentStatusEvent fromEntityLike(Like like){
        LikeCommentStatusEvent event = new LikeCommentStatusEvent();
        event.setAction("좋아요");
        event.setCommentLikeUserId(like.getUserInfo().getUserId());
        Long likeEvent = like.getComment().getId();
        event.setCommentId(String.valueOf(likeEvent));
        return event;
    }

    public static LikeCommentStatusEvent fromEntityCancel(Like like){
        LikeCommentStatusEvent event = new LikeCommentStatusEvent();
        event.setAction("취소");
        event.setCommentLikeUserId(like.getUserInfo().getUserId());
        Long cancelEvent = like.getComment().getId();
        event.setCommentId(String.valueOf(cancelEvent));
        return event;
    }
}

