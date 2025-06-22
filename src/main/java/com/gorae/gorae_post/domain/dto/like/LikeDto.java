package com.gorae.gorae_post.domain.dto.like;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDto {
//    TODO: userId 삭제(UserPrincipal로 받아와야됨)
    private String userId;
    private Long commentId;
}
