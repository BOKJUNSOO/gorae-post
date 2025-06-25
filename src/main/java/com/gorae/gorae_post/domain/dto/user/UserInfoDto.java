package com.gorae.gorae_post.domain.dto.user;

import com.gorae.gorae_post.domain.entity.UserInfo;
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

    public static UserInfoDto fromEntity(UserInfo userInfo) {
        return UserInfoDto.builder()
                .userId(userInfo.getUserId()) // 엔티티의 getter 메소드 이름에 맞게 수정
                .userName(userInfo.getUserName())
                .userBadge(userInfo.getUserBadge())
                .profileImgUrl(userInfo.getProfileImgUrl())
                .build();
    }
}
