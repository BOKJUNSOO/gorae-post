package com.gorae.gorae_post.api.open;

import com.gorae.gorae_post.common.dto.ApiResponseDto;
import com.gorae.gorae_post.domain.dto.comment.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/post/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CommentController {

    // 답변 생성 및 수정
    @PostMapping(value="/answer")
    public ApiResponseDto<String> createComment (@RequestBody Comment comment) {
        // TODO : 답변 저장 service
        return ApiResponseDto.defaultOk();
    }

    // 답변 상세 조회
    // 답변에 대한 댓글을 위해서라면 필요
    @GetMapping(value ="/detail/{commentId}")
    public ApiResponseDto<String> viewComment (@PathVariable("commentId") String commentId){
        // TODO : 답변 내용 상세조회 service
        return ApiResponseDto.defaultOk();
    }
}
