package com.gorae.gorae_post.domain.dto.question;

import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class QuestionDetailDto {

    private Long questionId;

    private String title;

    private Map<String,Object> detailContent;

    private UserInfoDto userInfo;

    private LocalDateTime updateAt;

    private int viewCount;

}
