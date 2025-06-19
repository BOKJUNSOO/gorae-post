package com.gorae.gorae_post.domain.dto.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserInfo {

    @Id
    @Column(name="user_id")
    private String userId;

    @Column(name="user_name")
    private String userName;

    @Column(name="profile_img_url")
    private String profileImgUrl;

    @Column(name="user_badge",nullable = true)
    private String userBadge;
}
