package com.gorae.gorae_post.domain.repository;

import com.gorae.gorae_post.domain.dto.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
