package com.gorae.gorae_post.domain.dto.comment;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gorae.gorae_post.domain.dto.like.Like;
import com.gorae.gorae_post.domain.dto.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String commentContent;

    @Column
    private String userId;

    @Column
    private LocalDateTime createAt;

    @Column
    private LocalDateTime updateAt;

    @Column(name = "like_count", nullable = false)
    private Long likeCount = 0L;

    @JsonIgnore
    @ManyToOne(targetEntity = Question.class)
    @JoinColumn(name = "questionId", referencedColumnName = "id",nullable = true)
    private Question question;

    @JsonIgnore
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public void increaseLikeCount(){
        this.likeCount++;
    }

    public void decreaseLikeCount(){
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
