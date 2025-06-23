package com.gorae.gorae_post.domain.dto.comment;

import com.gorae.gorae_post.domain.dto.question.Question;
import com.gorae.gorae_post.domain.dto.user.UserInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentCreateDto {
    @NotEmpty(message = "답글은 필수 입력값입니다.")
    @Size(max = 150)
    private String commentContent;


    public Comment toEntity(UserInfo userInfo, Question question) {
        Comment comment = new Comment();
        comment.setUserInfo(userInfo);
        comment.setCommentContent(this.commentContent);
        comment.setQuestion(question);
        comment.setAdopt(false);
        comment.setCreateAt(LocalDateTime.now());
        comment.setUpdateAt(LocalDateTime.now());
        return comment;
    }
}
