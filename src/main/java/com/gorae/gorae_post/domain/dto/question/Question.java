package com.gorae.gorae_post.domain.dto.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gorae.gorae_post.domain.dto.comment.Comment;
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

    @Column(name="question_title", nullable = false)
    private String questionTitle;

    @Column(columnDefinition = "TEXT",name="question_content",nullable = false)
    private String questionContent;

    @Column(name="user_id",nullable = false)
    private String userId; // JWT 추출

    @Column
    private LocalDateTime updateAt;

    @Column
    private Integer viewCount;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;
}
