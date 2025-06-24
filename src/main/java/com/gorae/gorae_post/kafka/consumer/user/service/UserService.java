package com.gorae.gorae_post.kafka.consumer.user.service;

import com.gorae.gorae_post.domain.dto.user.UserInfo;
import com.gorae.gorae_post.domain.repository.UserRepository;
import com.gorae.gorae_post.kafka.consumer.user.dto.ChangeUserInfoEvent;
import com.gorae.gorae_post.kafka.consumer.user.dto.UserInfoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void register(UserInfoEvent event) {
        UserInfo userInfo = new UserInfo();

        userInfo.setUserId(event.getUserId());
        userInfo.setUserName(event.getUserName());
        userInfo.setProfileImgUrl(event.getProfileImgUrl());

        userRepository.save(userInfo);
    }

    @Transactional
    public void changeUserInfo(ChangeUserInfoEvent event) {
        UserInfo userInfo = new UserInfo();

        String userId = event.getUserId();
        log.info("Info Change User:{}",userId);

        userInfo.setUserId(event.getUserId());
        userInfo.setUserName(event.getUserName());
        userInfo.setProfileImgUrl(event.getProfileImageUrl());
    }
}
