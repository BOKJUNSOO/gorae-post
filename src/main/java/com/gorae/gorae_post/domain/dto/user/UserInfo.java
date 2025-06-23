package com.gorae.gorae_post.domain.dto.user;

import com.gorae.gorae_post.domain.dto.comment.Comment;
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

    @Column(name="user_badge",nullable = true)
    private String userBadge = "0";

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
