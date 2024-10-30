package com.example.service;

import com.example.grpc.PodcastServiceClient;
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
    private final PodcastServiceClient podcastService = new PodcastServiceClient("localhost", 9090);

    public Episode createEpisode(Episode episode) {
        try {
            podcastService.getPodcast(episode.getPodcastId());
            return episodeRepository.save(episode);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create episode");
        }
    }

    public Episode getEpisode(int id) {
        return episodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Episode not found"));
    }

    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }
}
