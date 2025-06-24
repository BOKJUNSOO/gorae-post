package com.gorae.gorae_post.domain.dto.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorae.gorae_post.domain.dto.question.Question;
import com.gorae.gorae_post.domain.dto.user.UserInfo;
import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
public class CommentCreateDto {
    private Long questionId;

    @NotEmpty(message = "답글은 필수 입력값입니다.")
    @Size(max = 150)
    private Map<String,Object> commentContent;

    private UserInfoDto userInfo;

    private boolean adopt;

    private Long likeCount;

    private LocalDateTime createAt;

    public Comment toEntity(UserInfo userInfo, Question question) {
        Comment comment = new Comment();
        comment.setUserInfo(userInfo);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String contentJson = mapper.writeValueAsString(this.commentContent);
            comment.setCommentContent(contentJson);
        } catch (Exception e) {
            throw new RuntimeException("content는 json 타입입니다.",e);
        }
        comment.setQuestion(question);
        comment.setAdopt(false);
        comment.setCreateAt(LocalDateTime.now());
        comment.setUpdateAt(LocalDateTime.now());
        return comment;
    }
}
