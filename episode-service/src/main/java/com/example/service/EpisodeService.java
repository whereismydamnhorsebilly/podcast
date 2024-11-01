package com.example.service;

import com.example.kafka.KafkaProducer;
import com.example.model.Episode;
import com.example.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final KafkaProducer kafkaProducer;

    public Episode createEpisode(Episode episode) {
        Episode newEpisode = episodeRepository.save(episode);
        kafkaProducer.send("episode-topic", String.valueOf(newEpisode.getId()));
        return newEpisode;
    }

    public Episode getEpisode(int id) {
        return episodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Episode not found"));
    }

    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }
}
