package com.gorae.gorae_post.domain.dto.question;

import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class QuestionUpdateDto {

    private Long questionId;

    private String title;

    private Map<String,Object> content;

    private UserInfoDto userInfo;

    private LocalDateTime updateAt;

    private int viewCount;
}
