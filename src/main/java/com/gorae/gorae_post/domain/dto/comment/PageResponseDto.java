package com.gorae.gorae_post.domain.dto.comment;

import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class PageResponseDto<T> {
    private List<T> content; // 실제 데이터 리스트
    private int pageNumber;      // 현재 페이지 번호
    private int pageSize;        // 페이지 당 데이터 수
    private int totalPages;      // 전체 페이지 수
    private long totalElements;  // 전체 데이터 수
}
