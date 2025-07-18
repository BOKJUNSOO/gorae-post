package com.gorae.gorae_post.api.open;


import com.gorae.gorae_post.common.GatewayRequestHeaderUtils;
import com.gorae.gorae_post.common.dto.ApiResponseDto;
import com.gorae.gorae_post.domain.dto.like.LikeDto;
import com.gorae.gorae_post.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/post/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class LikeController {
    private final LikeService likeService;

    @PostMapping(value = "/like")
    public ApiResponseDto<Long> commentLike(@RequestBody @Valid LikeDto likeDto){
        String userId = GatewayRequestHeaderUtils.getUserId();
        Long likeCount = likeService.like(likeDto, userId);
        return ApiResponseDto.createOk(likeCount);
    }
}
