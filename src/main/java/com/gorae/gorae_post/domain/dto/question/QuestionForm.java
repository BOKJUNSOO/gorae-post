package com.gorae.gorae_post.domain.dto.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message = "제목은 필수항목 입니다.")
    @Size(max=200)
    private String title;

    @NotEmpty(message = "내용은 필수항목 입니다.")
    private String content;

    public Question toEntity(String userId) {
        Question question = new Question();
        question.setQuestionTitle(this.title);
        question.setQuestionContent(this.content);
        question.setUserId(userId);
        question.setUpdateAt(LocalDateTime.now());
        question.setViewCount(0);
        return question;
    }
}
