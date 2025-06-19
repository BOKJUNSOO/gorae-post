package com.gorae.gorae_post.domain.dto.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name="username")
    private String userName;

    @Column(name="profile_img_url")
    private String profileImgUrl;
}
