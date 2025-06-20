package com.gorae.gorae_interaction.kafka.producer.leaderboard.service;

import com.gorae.gorae_interaction.kafka.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
