package com.gorae.gorae_post.domain.dto.comment;

import com.gorae.gorae_post.domain.dto.user.UserInfo;
import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long commentId;

    private Long likeCount;

    private UserInfoDto userInfo;

    private String commentContent;

    private boolean commentAdopt;



}
