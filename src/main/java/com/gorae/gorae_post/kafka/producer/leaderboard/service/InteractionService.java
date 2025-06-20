package com.gorae.gorae_interaction.kafka.producer.leaderboard.service;

public interface InteractionService{
    void commentLike(Long commentId, Long userId);
    void commentLikeCancel(Long commentId, Long userId);
}
