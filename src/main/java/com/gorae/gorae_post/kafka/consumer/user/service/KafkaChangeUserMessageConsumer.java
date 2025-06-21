package com.gorae.gorae_post.kafka.consumer.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaChangeUserMessageConsumer {
    // TODO : 회원가입 이외의 user 에서 발생하는 어떤 이벤트 (회원 탈퇴, userName 변경 등등) 처리 로직
}
