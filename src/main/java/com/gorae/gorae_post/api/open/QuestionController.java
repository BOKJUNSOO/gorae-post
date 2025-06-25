package com.gorae.gorae_post.api.open;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gorae.gorae_post.common.GatewayRequestHeaderUtils;
import com.gorae.gorae_post.common.dto.ApiResponseDto;
import com.gorae.gorae_post.domain.dto.question.*;
import com.gorae.gorae_post.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/post/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


    // 질문 생성
    @PostMapping(value = "/questions/create")
    public ApiResponseDto<Long> createQuestion (@RequestBody @Valid QuestionCreateDto questionCreateDto){
        String userId = GatewayRequestHeaderUtils.getUserId();
        Long questionId = questionService.create(questionCreateDto, userId);
        return ApiResponseDto.createOk(questionId);
    }

    // 질문 수정
    @PostMapping(value = "/questions/update")
    public ApiResponseDto<QuestionDetailDto> updateQuestion (@RequestBody @Valid QuestionUpdateDto questionUpdateDto) throws AccessDeniedException, ChangeSetPersister.NotFoundException, JsonProcessingException {
        // TODO : GateWay , JWT 프로퍼티 맞추고 사용할 것
        String userId = GatewayRequestHeaderUtils.getUserId();
        questionService.update(questionUpdateDto, userId);
        QuestionDetailDto questionDetailDto = questionService.detail(questionUpdateDto.getQuestionId());
        return ApiResponseDto.createOk(questionDetailDto);
    }

    // 질문 삭제
    @PostMapping(value = "/questions/delete")
    public ApiResponseDto<String> deleteQuestion (@RequestBody Map<String, Long> payload) throws ChangeSetPersister.NotFoundException, AccessDeniedException {
        String userId = GatewayRequestHeaderUtils.getUserId();
        Long questionId = payload.get("questionId");
        questionService.delete(questionId,userId);
        return ApiResponseDto.createOk("게시글이 삭제됐습니다.");
    }

    // 질문 Overview
    @GetMapping(value = "/auth/questions")
    public ApiResponseDto<QuestionListDto> overviewQuestions (
            @RequestParam(value = "offset", defaultValue = "10") int size,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "keyword",required = false) String keyword,
            @RequestParam(value = "sort", defaultValue = "updateAt") String sort,
            @RequestParam(value = "order", defaultValue = "desc") String order
    ) {
        QuestionListDto data;
        if (keyword == null || keyword.isBlank()) {
            data = questionService.overview(page,size,keyword,sort,order);
        } else {
            data = questionService.search(page,size,keyword,sort,order);
        }
        return ApiResponseDto.createOk(data);
    }

    // 질문 상세 조회
    @GetMapping(value = "/auth/questions/detail")
    public ApiResponseDto<QuestionDetailDto> viewQuestion (@RequestParam(value = "questionId") Long questionId) throws ChangeSetPersister.NotFoundException, JsonProcessingException {
        QuestionDetailDto questionDetailDto = questionService.detail(questionId);
        return ApiResponseDto.createOk(questionDetailDto);
    }
}
