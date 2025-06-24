package com.gorae.gorae_post.kafka.consumer.user.service;


import com.gorae.gorae_post.domain.dto.user.UserInfo;
import com.gorae.gorae_post.domain.repository.UserRepository;
import com.gorae.gorae_post.kafka.consumer.user.dto.UserInfoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaUserMessageConsumer {
    private final UserRepository userRepository;

    @KafkaListener(topics = UserInfoEvent.TOPIC, properties = {JsonDeserializer.VALUE_DEFAULT_TYPE + ":com.gorae.gorae_post.kafka.consumer.user.dto.UserInfoEvent"})
    void handleSiteUserInfoEvent(UserInfoEvent event, Acknowledgment ack) {
        System.out.println("debugging");
        log.info("Received event : {}",event);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(event.getUserId());
        userInfo.setUserName(event.getUserName());
        userInfo.setProfileImgUrl(event.getProfileImgUrl());
        userRepository.save(userInfo);
        ack.acknowledge();
    }
}