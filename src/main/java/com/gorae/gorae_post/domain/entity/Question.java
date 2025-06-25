package com.gorae.gorae_post.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",name="contentJson",nullable = false)
    @Lob
    private String contentJson;

    @Column(name="user_id",nullable = false)
    private String userId; // JWT 추출

    @Column
    private LocalDateTime updateAt;

    @Column
    private Integer viewCount;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @Column
    private Boolean display = true;

}
