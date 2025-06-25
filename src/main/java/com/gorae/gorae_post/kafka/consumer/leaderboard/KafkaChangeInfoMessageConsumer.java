package com.gorae.gorae_post.kafka.consumer.leaderboard;


import com.gorae.gorae_post.kafka.consumer.leaderboard.dto.UserStatusEvent;
import com.gorae.gorae_post.kafka.consumer.leaderboard.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaChangeInfoMessageConsumer {
    private final UserInfoService userInfoService;

    @KafkaListener(topics = UserStatusEvent.TOPIC,
    properties = {JsonDeserializer.VALUE_DEFAULT_TYPE + ":com.gorae.gorae_post.kafka.consumer.leaderboard.dto.UserStatusEvent"})
    void handleChangeBadgeEvent(UserStatusEvent event, Acknowledgment ack){
        log.info("받은 정보", event);
        userInfoService.ChangeBadge(event);
        ack.acknowledge();
    }
}
