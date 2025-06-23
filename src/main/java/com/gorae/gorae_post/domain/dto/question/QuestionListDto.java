package com.gorae.gorae_post.domain.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionListDto {

    private List<QuestionOverviewDto> questions;

    private int page;

    private int offset;

    private long totalPages;

    private long totalElements;
}
