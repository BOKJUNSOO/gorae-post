package com.gorae.gorae_post.domain.repository;

import com.gorae.gorae_post.domain.dto.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.question.id = :questionId AND c.adopt = true")
    int getCommentAdoptExisted(@Param("questionId") Long question_id);
}
