package com.gorae.gorae_post.domain.dto.question;

import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class QuestionOverviewDto {

    private String keyword;

    private Long questionId;

    private UserInfoDto userInfoDto;

    private String title;

    private Map<String,Object> previewContent;

    private String writer;
}
