package com.gorae.gorae_post.api.open;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    @CrossOrigin()
    @PostMapping(value="/questions/answer/create")
    public ApiResponseDto<CommentCreateDto> createComment (@RequestBody @Valid CommentCreateDto commentCreateDto) throws JsonProcessingException {
//        TODO: @AuthenticaionPrincipal로  userId 받아오기
        String userId = "seungwon";
        CommentCreateDto commentContent = commentService.createComment(commentCreateDto, userId);
        return ApiResponseDto.createOk(commentContent);
    }

    @CrossOrigin()
    @PostMapping(value = "/questions/answer/update")
    public ApiResponseDto<CommentUpdateDto> updateComment (@RequestBody CommentUpdateDto commentUpdateDto) throws AccessDeniedException, JsonProcessingException {
//        TODO: @AuthenticaionPrincipal로  userId 받아오기
        String userId = "seungwon";
        CommentUpdateDto updateDate = commentService.updateComment(commentUpdateDto, userId);
        return ApiResponseDto.createOk(updateDate);
    }

    @CrossOrigin()
    @PostMapping(value = "/questions/answer/delete")
    public ApiResponseDto<String> deleteComment (@RequestBody Map<String, Long> payload) throws AccessDeniedException {
//        TODO: @AuthenticaionPrincipal로  userId 받아오기
        String userId = "seungwon";
        Long commentId = payload.get("commentId");
        commentService.deleteComment(commentId, userId);
        return ApiResponseDto.createOk("삭제가 완료되었습니다");
    }

    @CrossOrigin()
    @PostMapping(value = "/questions/answer/adopt")
    public ApiResponseDto<Long> adoptComment (@RequestBody CommentAdoptDto commentAdoptDto) throws AccessDeniedException {
//        TODO: @AuthenticaionPrincipal로  userId 받아오기
        String userId = "bok";
        Long adoptCommentId = commentService.adoptComment(commentAdoptDto, userId);
        return ApiResponseDto.createOk(adoptCommentId);
    }

    // 답변 상세 조회
    // 답변에 대한 댓글을 위해서라면 필요
    @CrossOrigin()
    @GetMapping(value ="/comments/detail")
    public ApiResponseDto<PageResponseDto<CommentDto>> viewComment(
            @RequestParam(value = "questionId") Long questionId,
            @RequestParam(defaultValue = "1") int offset,
            @RequestParam(defaultValue = "5") int page){
        Pageable pageable = PageRequest.of(offset / page, page);
        PageResponseDto<CommentDto> response = commentService.commentView(questionId, pageable);
        return ApiResponseDto.createOk(response);
    }
}
