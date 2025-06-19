package com.gorae.gorae_post.service;

import com.gorae.gorae_post.domain.dto.question.Question;
import com.gorae.gorae_post.domain.dto.question.QuestionForm;
import com.gorae.gorae_post.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public void create(QuestionForm questionForm, String userId) {
        Question question = questionForm.toEntity(userId);
        questionRepository.save(question);
    }

    @Transactional
    public void update(QuestionForm questionForm, String userId, Long questionId) throws ChangeSetPersister.NotFoundException, AccessDeniedException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        if (!question.getUserId().equals(userId)) {
            throw new AccessDeniedException("본인 글만 수정이 가능합니다.");
        }

        question.setQuestionTitle(questionForm.getTitle());
        question.setQuestionContent(questionForm.getContent());
        question.setUpdateAt(LocalDateTime.now());
    }
}
