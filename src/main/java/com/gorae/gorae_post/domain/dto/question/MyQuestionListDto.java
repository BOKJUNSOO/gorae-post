package com.gorae.gorae_post.domain.dto.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MyQuestionListDto {

    private List<MyQuestionDto> questions;

    private int page;

    private int offset;

    private long totalPages;

    private long totalElements;
}
