package com.gorae.gorae_post.domain.dto.question;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class QuestionUpdateDto {

    private Long questionId;

    @NotEmpty
    private String title;

    @NotEmpty
    private Map<String,Object> content;
}
