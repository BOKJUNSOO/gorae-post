package com.gorae.gorae_post.api.open;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gorae.gorae_post.common.dto.ApiResponseDto;
import com.gorae.gorae_post.domain.dto.question.QuestionActionRequest;
import com.gorae.gorae_post.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestController
@RequestMapping(value = "/api/post/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 질문 Overview
    @GetMapping(value = "questions")
    public ApiResponseDto<String> overviewQuestions (
            @RequestParam(value = "offset", defaultValue = "10") int offset,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        // TODO : 검색에 따른 질문 미리보기 service
        return ApiResponseDto.defaultOk();
    }

    // 질문 상세 조회
    @GetMapping(value = "/detail/{questionId}")
    public ApiResponseDto<String> viewQuestion (@PathVariable("questionId") Long questionId) throws ChangeSetPersister.NotFoundException, JsonProcessingException {
        String data = questionService.detail(questionId);
        return ApiResponseDto.createOk(data);
    }

    // 질문 생성, 수정, 삭제
    @PostMapping(value = "/questions/action")
    public ApiResponseDto<String> createQuestion (@RequestBody @Valid QuestionActionRequest actionRequest) throws ChangeSetPersister.NotFoundException, AccessDeniedException {
        // TODO : JWT 로부터 userId 파싱
        String userId = "bok";

        String type = actionRequest.getType();
        switch (type) {
            case "create":
                questionService.create(actionRequest,userId);
                break;
            case "update":
                questionService.update(actionRequest,userId);
                break;
            case "delete":
                // TODO : 삭제 서비스 작성
                questionService.delete();
                break;
            default:
                throw new IllegalArgumentException("type 값을 확인해주세요");
        }
        return ApiResponseDto.createOk(userId);
    }

    // 질문 조회수 별 조회
    @GetMapping
    public ApiResponseDto<String> viewTopQuestion () {
        return ApiResponseDto.defaultOk();
    }
}
