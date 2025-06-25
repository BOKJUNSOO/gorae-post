package com.gorae.gorae_post.api.open;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gorae.gorae_post.common.GatewayRequestHeaderUtils;
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
    @PostMapping(value = "/questions/answer/create")
    public ApiResponseDto<CommentCreateDto> createComment(@RequestBody @Valid CommentCreateDto commentCreateDto) throws JsonProcessingException {
//        TODO: @AuthenticaionPrincipal로  userId 받아오기
        String userId = GatewayRequestHeaderUtils.getUserId();
                CommentCreateDto commentContent = commentService.createComment(commentCreateDto, userId);
        return ApiResponseDto.createOk(commentContent);
    }

    @PostMapping(value = "/questions/answer/update")
    public ApiResponseDto<CommentUpdateDto> updateComment(@RequestBody CommentUpdateDto commentUpdateDto) throws AccessDeniedException, JsonProcessingException {
        String userId = GatewayRequestHeaderUtils.getUserId();
        CommentUpdateDto updateDate = commentService.updateComment(commentUpdateDto, userId);
        return ApiResponseDto.createOk(updateDate);
    }

    @PostMapping(value = "/questions/answer/delete")
    public ApiResponseDto<String> deleteComment(@RequestBody Map<String, Long> payload) throws AccessDeniedException {
        String userId = GatewayRequestHeaderUtils.getUserId();
        Long commentId = payload.get("commentId");
        commentService.deleteComment(commentId, userId);
        return ApiResponseDto.createOk("삭제가 완료되었습니다");
    }

    @PostMapping(value = "/questions/answer/adopt")
    public ApiResponseDto<Long> adoptComment(@RequestBody CommentAdoptDto commentAdoptDto) throws AccessDeniedException {
        String userId = GatewayRequestHeaderUtils.getUserId();
        Long adoptCommentId = commentService.adoptComment(commentAdoptDto, userId);
        return ApiResponseDto.createOk(adoptCommentId);
    }

    // 답변 상세 조회
    // 답변에 대한 댓글을 위해서라면 필요
    @GetMapping(value = "/auth/comments/detail")
    public ApiResponseDto<PageResponseDto<CommentDto>> viewComment(
            @RequestParam(value = "questionId") Long questionId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int offset) {
        Pageable pageable = PageRequest.of(page, offset);
        PageResponseDto<CommentDto> response = commentService.commentView(questionId, pageable);
        return ApiResponseDto.createOk(response);
    }
}
