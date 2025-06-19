package com.gorae.gorae_post.domain.dto.comment;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gorae.gorae_post.domain.dto.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer questionId; // FK question.id

    @Column(columnDefinition = "TEXT")
    private String commentContent;

    @Column
    private String userId;

    @Column
    private LocalDateTime createAt;

    @Column
    private LocalDateTime updateAt;

    @JsonIgnore
    @ManyToOne
    private Question question;
}
