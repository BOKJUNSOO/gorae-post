package com.gorae.gorae_post.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "comments")
public class UserInfo {

    @Id
    @Column(name="user_id")
    private String userId;

    @Column(name="user_name")
    private String userName;

    @Column(name="profile_img_url")
    private String profileImgUrl;

    // 채택율에 따른 뱃지 (consumer 에서는 userBadge)
    @Column(name="user_badge",nullable = true)
    private String userBadge = "1";

    // 좋아요 받은 갯수에 따른 뱃지 (consumer 에서는 likeBadge)
    @Column(name="like_badge",nullable = true)
    private String likeBadge = "1";

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
