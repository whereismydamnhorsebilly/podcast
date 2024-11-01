package com.example.listener.episodeListener;

import com.example.service.StatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEpisodeListener {

    private final StatServiceImpl statServiceImpl;

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "episode-topic",
            groupId = "${spring.kafka.consumer.episode-id}")
    void listenerEpisode(String message) {
        System.out.println("Listener received a new episode with Id: " + message);
        statServiceImpl.updateEpisodeCount();
        System.out.println("Current episode number is " + statServiceImpl.getEpisodeCount());
    }
}
