package com.gorae.gorae_post.api.open;

import com.gorae.gorae_post.common.dto.ApiResponseDto;
import com.gorae.gorae_post.domain.dto.comment.CommentAdoptDto;
import com.gorae.gorae_post.domain.dto.comment.CommentCreateDto;
import com.gorae.gorae_post.domain.dto.comment.PageResponseDto;
import com.gorae.gorae_post.domain.dto.comment.CommentUpdateDto;
import com.gorae.gorae_post.domain.dto.question.CommentDto;
import com.gorae.gorae_post.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Map;

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
//        TODO: @AuthenticaionPrincipal로  userId 받아오기
        String userId = "seungwon";
        Long updateId = commentService.updateComment(commentUpdateDto, userId);
        return ApiResponseDto.createOk(updateId);
    }

    @PostMapping(value = "/question/answer/delete")
    public ApiResponseDto<String> deleteComment (@RequestBody Map<String, Long> payload) throws AccessDeniedException {
//        TODO: @AuthenticaionPrincipal로  userId 받아오기
        String userId = "seungwon";
        Long commentId = payload.get("commentId");
        commentService.deleteComment(commentId, userId);
        return ApiResponseDto.createOk("삭제가 완료되었습니다");
    }

    @PostMapping(value = "/question/answer/adopt")
    public ApiResponseDto<Long> adoptComment (@RequestBody CommentAdoptDto commentAdoptDto) throws AccessDeniedException {
//        TODO: @AuthenticaionPrincipal로  userId 받아오기
        String userId = "bok";
        Long adoptCommentId = commentService.adoptComment(commentAdoptDto, userId);
        return ApiResponseDto.createOk(adoptCommentId);
    }

    // 답변 상세 조회
    // 답변에 대한 댓글을 위해서라면 필요
    @GetMapping(value ="/detail")
    public ApiResponseDto<PageResponseDto<CommentDto>> viewComment(
            @RequestParam(value = "questionId") Long questionId,
            @RequestParam(defaultValue = "1") int offset,
            @RequestParam(defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(offset / limit, limit);
        PageResponseDto<CommentDto> response = commentService.commentView(questionId, pageable);
        return ApiResponseDto.createOk(response);
    }
}
