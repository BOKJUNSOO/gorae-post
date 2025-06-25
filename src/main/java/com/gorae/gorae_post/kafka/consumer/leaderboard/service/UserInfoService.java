package com.gorae.gorae_post.kafka.consumer.leaderboard.service;

import com.gorae.gorae_post.common.exception.NotFound;
import com.gorae.gorae_post.domain.entity.UserInfo;
import com.gorae.gorae_post.domain.repository.UserRepository;
import com.gorae.gorae_post.kafka.consumer.leaderboard.dto.UserStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;

    @Transactional
    public void ChangeBadge(UserStatusEvent event){
        String userId = event.getUserId();
        UserInfo userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new NotFound("존재하지 않는 사용자입니다."));
        userInfo.setUserBadge(event.getUserBadge());
        userInfo.setLikeBadge(event.getLikeBadge());
        userRepository.save(userInfo);
    }
}
