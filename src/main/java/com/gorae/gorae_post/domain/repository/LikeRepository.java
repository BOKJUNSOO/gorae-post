package com.gorae.gorae_post.domain.repository;

import com.gorae.gorae_post.domain.entity.Comment;
import com.gorae.gorae_post.domain.entity.Like;
import com.gorae.gorae_post.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository  extends JpaRepository<Like,Long>{

    Optional<Like> findByUserInfoAndComment(UserInfo userInfo, Comment comment);
}
