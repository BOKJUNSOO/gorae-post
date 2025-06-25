package com.gorae.gorae_post.domain.repository;

import com.gorae.gorae_post.domain.entity.Comment;
import com.gorae.gorae_post.domain.entity.Like;
import com.gorae.gorae_post.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LikeRepository  extends JpaRepository<Like,Long>{

    Optional<Like> findByUserInfoAndComment(UserInfo userInfo, Comment comment);
    boolean existsByCommentIdAndUserInfo_UserIdAndLikeStatusIsTrue(Long commentId, String userId);
}
