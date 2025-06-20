package com.gorae.gorae_post.domain.dto.question;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionActionRequest {

    // create , update, delete
    @NotEmpty(message = "type은 필수값입니다.")
    private String type;

    private Long questionId;

    @NotEmpty
    private QuestionForm payload;
}
