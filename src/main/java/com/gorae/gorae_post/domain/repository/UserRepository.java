package com.gorae.gorae_post.domain.repository;

import com.gorae.gorae_post.domain.dto.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
}
