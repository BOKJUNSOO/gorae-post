package com.gorae.gorae_post.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorae.gorae_post.common.dto.ApiResponseDto;
import com.gorae.gorae_post.domain.dto.comment.Comment;
import com.gorae.gorae_post.domain.dto.question.Question;
import com.gorae.gorae_post.domain.dto.question.QuestionActionRequest;
import com.gorae.gorae_post.domain.dto.question.QuestionForm;
import com.gorae.gorae_post.domain.dto.user.UserInfo;
import com.gorae.gorae_post.domain.repository.QuestionRepository;
import com.gorae.gorae_post.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(QuestionActionRequest actionRequest, String userId) {
        QuestionForm questionForm = actionRequest.getPayload();
        Question question = questionForm.toEntity(userId);
        questionRepository.save(question);
    }

    @Transactional
    public void update(QuestionActionRequest actionRequest, String userId) throws ChangeSetPersister.NotFoundException, AccessDeniedException {
        Long questionId = actionRequest.getQuestionId();
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        if (question.getUserId().equals(userId)) {
            throw new AccessDeniedException("본인 글만 수정이 가능합니다.");
        }
        question.setQuestionTitle(question.getQuestionTitle());
        question.setQuestionContent(question.getQuestionContent());
        question.setUpdateAt(LocalDateTime.now());
    }

    @Transactional
    public void delete() {

    }


    @Transactional
    public String detail(Long questionId) throws ChangeSetPersister.NotFoundException, JsonProcessingException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        String userId = question.getUserId();
        Optional<UserInfo> userInfo = userRepository.findById(userId);
        Map<String, Object> map = Map.of(
                "question", question,
                "userInfo", userInfo
        );

        return new ObjectMapper().writeValueAsString(map);
    }
}
