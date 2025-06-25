package com.gorae.gorae_post.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment_like")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    Comment comment;

    @Column(nullable = false)
    Boolean likeStatus = false;

    @Builder
    public Like (UserInfo userInfo, Comment comment, Boolean likeStatus){
        this.userInfo = userInfo;
        this.comment = comment;
        this.likeStatus = likeStatus;
    }

}
