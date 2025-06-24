package com.gorae.gorae_post.kafka.consumer.user;

import com.gorae.gorae_post.kafka.consumer.user.dto.ChangeUserInfoEvent;
import com.gorae.gorae_post.kafka.consumer.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaChangeUserMessageConsumer {
    private final UserService userService;

    @KafkaListener(topics = ChangeUserInfoEvent.TOPIC, properties = {JsonDeserializer.VALUE_DEFAULT_TYPE + ":com.gorae.gorae_post.kafka.consumer.user.dto.ChangeUserInfoEvent"})
    void handleChangeSiteUserEvent(ChangeUserInfoEvent event, Acknowledgment ack) {
        log.info("Received event: {}", event);
        userService.changeUserInfo(event);
        ack.acknowledge();
    }
}
