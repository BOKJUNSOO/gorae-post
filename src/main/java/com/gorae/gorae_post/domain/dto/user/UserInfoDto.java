package com.gorae.gorae_post.domain.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoDto {
    private String userId;

    private String userName;

    private String profileImgUrl;

    private String userBadge;
}
