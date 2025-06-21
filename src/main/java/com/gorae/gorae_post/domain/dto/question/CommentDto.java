package com.gorae.gorae_post.domain.dto.question;

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

    private UserInfoDto userInfoDto;
}
