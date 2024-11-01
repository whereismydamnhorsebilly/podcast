package com.example.listener.podcastListener;

import com.example.dto.SimpleStatDto;
import com.example.service.StatService;
import com.example.service.StatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class KafkaPodcastListener {

    private final StatService statService;

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "podcast-topic",
            groupId = "${spring.kafka.consumer.podcast-id}")
    void listenerPodcast(String message) {
        System.out.println("Listener received a new podcast with Id: " + message);
        statService.updatePodcastCount();

        SimpleStatDto simpleStatDto = SimpleStatDto.builder()
                .podcastId(Long.parseLong(message))
                .podcastRegister(LocalDateTime.now())
                .build();
        statService.createSimpleStat(simpleStatDto);

        System.out.println("Current podcast number is " + statService.getPodcastCount());
    }
}
