package com.gorae.gorae_post.domain.repository;

import com.gorae.gorae_post.domain.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByDisplayTrue(Pageable pageable);
    Page<Question> findByDisplayTrueAndTitleContainingOrContentJsonContaining(String titleContain,String ContentContain, Pageable page);
    Page<Question> findByUserIdAndDisplayTrue(String userId,Pageable page);
}
