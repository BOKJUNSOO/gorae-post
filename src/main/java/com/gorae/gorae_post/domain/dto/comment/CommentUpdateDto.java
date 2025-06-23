package com.gorae.gorae_post.domain.dto.comment;


import com.gorae.gorae_post.domain.dto.question.Question;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentUpdateDto {

    private Long commentId;

    @NotEmpty(message = "답글은 필수 입력값입니다.")
    @Size(max = 150)
    private String commentContent;
}
