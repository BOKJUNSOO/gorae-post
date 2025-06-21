package com.gorae.gorae_post.domain.dto.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class QuestionCreateDto {
    @NotEmpty(message = "제목은 필수항목 입니다.")
    @Size(max=200)
    private String title;

    @NotEmpty(message = "내용은 필수항목 입니다.")
    private Map<String,Object> content;

    public Question toEntity(String userId) {
        Question question = new Question();
        question.setTitle(this.title);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String contentJson = mapper.writeValueAsString(this.content);
            question.setContentJson(contentJson);
        } catch (Exception e) {
            throw new RuntimeException("content는 json 타입입니다.",e);
        }
        question.setUserId(userId);
        question.setUpdateAt(LocalDateTime.now());
        question.setViewCount(0);
        return question;
    }
}
