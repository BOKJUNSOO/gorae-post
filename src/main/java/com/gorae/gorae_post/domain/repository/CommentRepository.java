package com.gorae.gorae_post.domain.repository;

import com.gorae.gorae_post.domain.dto.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.userInfo WHERE c.question.id = :questionId",
            countQuery = "SELECT count(c) FROM Comment c WHERE c.question.id = :questionId")
    Page<Comment> findByQuestionIdWithUser(@Param("questionId") Long questionId, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.question.id = :questionId AND c.adopt = true")
    int getCommentAdoptExisted(@Param("questionId") Long question_id);
}
