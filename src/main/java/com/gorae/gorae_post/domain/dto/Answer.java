package com.gorae.gorae_post.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String commentContent;

    @Column
    private Integer questionId; // FK question.id

    @Column
    private Integer userId;

    @Column
    private LocalDateTime createAt;

    @Column
    private LocalDateTime updateAt;

    @JsonIgnore
    @ManyToOne
    private Question question;

}
