package com.gorae.gorae_post.domain.dto.question;

import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class MyQuestionDto {
    private Long questionId;

    private String title;

}
