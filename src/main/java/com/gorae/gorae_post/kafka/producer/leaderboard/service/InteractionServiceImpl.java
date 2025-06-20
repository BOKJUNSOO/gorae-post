package com.gorae.gorae_post.kafka.producer.leaderboard.service;

import com.gorae.gorae_post.kafka.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InteractionServiceImpl implements InteractionService {
    private final KafkaMessageProducer kafkaMessageProducer;

    @Override
    public void commentLike(Long commentId, Long userId) {

    }

    @Override
    public void commentLikeCancel(Long commentId, Long userId) {

    }
}
