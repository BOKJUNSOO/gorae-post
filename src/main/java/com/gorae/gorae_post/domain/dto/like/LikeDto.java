package com.gorae.gorae_post.domain.dto.like;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeStatusDto {
    private Long user_id;
    private Long question_id;
    private Long comment_id;

    public LikeStatusDto(Long user_id, Long question_id, Long comment_id){
        this.user_id = user_id;
        this.question_id = question_id;
        this.comment_id = comment_id;
    }
}
