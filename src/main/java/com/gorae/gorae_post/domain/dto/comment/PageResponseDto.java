package com.gorae.gorae_post.domain.dto.comment;

import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponseDto<T> {
    private List<T> content;
    private int page;
    private int offset;
    private int totalPages;
    private long totalElements;

    public PageResponseDto(Page<?> page, List<T> content) {
        this.content = content;
        this.page = page.getNumber()+1;
        this.offset = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
