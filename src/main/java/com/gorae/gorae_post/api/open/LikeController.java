package com.gorae.gorae_interaction.api.open;


import com.gorae.gorae_interaction.common.dto.ApiResponseDto;
import com.gorae.gorae_interaction.common.exception.ApiError;
import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(value = "/api/post/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class InteractionController {

    @PostMapping(value = "/{commentId}/like")
    public ApiResponseDto<String> commentLike(@PathVariable Long commentId, String userId){
//        TODO: @AuthenticationPrincipal UserPrincipal userPrincipal로 유저 아이디 구현
        return ApiResponseDto.defaultOk();
    }
}
