package com.gorae.gorae_post.domain.repository;

import com.gorae.gorae_post.domain.dto.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
