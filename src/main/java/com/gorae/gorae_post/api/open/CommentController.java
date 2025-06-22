package com.gorae.gorae_post.api.open;

import com.gorae.gorae_post.common.dto.ApiResponseDto;
import com.gorae.gorae_post.domain.dto.comment.Comment;
import com.gorae.gorae_post.domain.dto.comment.CommentCreateDto;
import com.gorae.gorae_post.domain.dto.comment.CommentUpdateDto;
import com.gorae.gorae_post.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestController
@RequestMapping(value = "/api/post/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CommentController {
private final CommentService commentService;

    // 답변 생성 및 수정
    @PostMapping(value="/question/answer/create")
    public ApiResponseDto<Long> createComment (@RequestBody @Valid CommentCreateDto commentCreateDto,
                                                 @RequestParam Long questionId) {
//        TODO: @AuthenticaionPrincipal로  userId 받아오기
        String userId = "seungwon";
        Long commentId = commentService.createComment(commentCreateDto, userId, questionId);
        return ApiResponseDto.createOk(commentId);
    }

    @PostMapping(value = "/question/answer/update")
    public ApiResponseDto<Long> updateComment (@RequestBody CommentUpdateDto commentUpdateDto) throws AccessDeniedException {
        String userId = "seungwon";
        Long updateId = commentService.updateComment(commentUpdateDto, userId);
        return ApiResponseDto.createOk(updateId);
    }


    // 답변 상세 조회
    // 답변에 대한 댓글을 위해서라면 필요
    @GetMapping(value ="/detail/{commentId}")
    public ApiResponseDto<String> viewComment (@PathVariable("commentId") String commentId){
        // TODO : 답변 내용 상세조회 service
        return ApiResponseDto.defaultOk();
    }
}
