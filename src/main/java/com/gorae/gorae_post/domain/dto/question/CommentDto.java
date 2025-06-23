package com.gorae.gorae_post.domain.dto.question;

import com.gorae.gorae_post.domain.dto.comment.Comment;
import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentDto {
    private Long commentId;

    private String commentContent;

    private LocalDateTime updateAt;

    private Long likeCount;

    private boolean adopt;

    private UserInfoDto userInfoDto;

    public static CommentDto fromEntity(Comment comment) {
        return CommentDto.builder()
                .commentId(comment.getId())
                .commentContent(comment.getCommentContent())
                .likeCount(comment.getLikeCount())
                .adopt(comment.isAdopt()) // 필드 이름이 adopt라면 isAdopt()
                .updateAt(comment.getUpdateAt())
                // UserInfo 엔티티를 UserInfoDto로 변환하여 포함시킵니다.
                .userInfoDto(UserInfoDto.fromEntity(comment.getUserInfo()))
                .build();
    }
}
