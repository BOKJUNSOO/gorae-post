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
                .adopt(comment.isAdopt())
                .updateAt(comment.getUpdateAt())
                .userInfoDto(UserInfoDto.fromEntity(comment.getUserInfo()))
                .build();
    }
}
