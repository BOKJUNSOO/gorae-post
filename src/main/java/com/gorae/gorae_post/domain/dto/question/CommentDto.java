package com.gorae.gorae_post.domain.dto.question;

import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
public class CommentDto {
    private Long commentId;

    private Map<String,Object> commentContent;

    private LocalDateTime updateAt;

    private Long likeCount;

    private boolean adopt;

    private UserInfoDto userInfoDto;
}
