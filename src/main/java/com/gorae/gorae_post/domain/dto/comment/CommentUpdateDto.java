package com.gorae.gorae_post.domain.dto.comment;


import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class CommentUpdateDto {

    private Long questionId;

    private Long commentId;

    private boolean adopt;

    private Long likeCount;

    @NotEmpty(message = "답글은 필수 입력값입니다.")
    @Size(max = 150)
    private Map<String,Object> commentContent;

    private UserInfoDto userInfoDto;

}
