package com.gorae.gorae_post.domain.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    @NotEmpty(message = "내용은 필수 항목 입니다.")
    private String comment;
}
