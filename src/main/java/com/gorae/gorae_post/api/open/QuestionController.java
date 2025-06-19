package com.gorae.gorae_post.api.open;

import com.gorae.gorae_post.common.dto.ApiResponseDto;
import com.gorae.gorae_post.domain.dto.question.QuestionForm;
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

    // 질문 생성
    @PostMapping(value = "/question")
    public ApiResponseDto<String> createQuestion (@RequestBody @Valid QuestionForm questionForm) {
        // TODO : JWT 로부터 userId 파싱
        String userId = "bok";
        // TODO : S3 에 이미지 적재 하는 로직 추가
        questionService.create(questionForm,userId);
        // TODO : User 서비스가 구독하는 question-create topic 에 메세지 발행
        return ApiResponseDto.createOk(userId);
    }

    // 질문 수정
    @PostMapping(value = "/question/{questionId}")
    public ApiResponseDto<String> updateQuestion (@PathVariable("questionId") Long questionId,
                                                  @RequestBody @Valid QuestionForm questionForm) throws AccessDeniedException, ChangeSetPersister.NotFoundException {
        // TODO : JWT 로부터 userId 파싱
        String userId = "bok";
        questionService.update(questionForm, userId, questionId);
        return ApiResponseDto.createOk(userId);
    }

    // 질문 상세 조회
    @GetMapping(value = "/detail/{questionId}")
    public ApiResponseDto<String> viewQuestion (@PathVariable("questionId") Long questionId){
        // TODO : 질문 상세조회 service
        return ApiResponseDto.defaultOk();
    }

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

    // 질문 삭제
    @PostMapping(value = "/delete/{questionId}")
    public ApiResponseDto<String> deleteQuestion (@PathVariable("questionId") Long questionId) {
        // TODO : 질문 삭제 service ( 답변이 존재한다면 삭제 불가 )
        return ApiResponseDto.defaultOk();
    }

    // 질문 조회수 별 조회
    @GetMapping
    public ApiResponseDto<String> viewTopQuestion () {
        return ApiResponseDto.defaultOk();
    }
}
