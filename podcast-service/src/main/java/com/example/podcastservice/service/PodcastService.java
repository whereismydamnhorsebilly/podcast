package com.example.podcastservice.service;

import com.example.podcastservice.exception.PodcastNotFoundException;
import com.example.podcastservice.model.Podcast;
import com.example.podcastservice.repository.PodcastRepository;
import com.example.podcastservice.utils.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PodcastService {

    private final PodcastRepository podcastRepository;

    public Podcast createPodcast(Podcast podcast) {
        return podcastRepository.save(podcast);
    }

    public Podcast getPodcast(int id) {
        return podcastRepository.findById(id)
                .orElseThrow(() -> new PodcastNotFoundException("No such podcast"));
    }

    public List<Podcast> getPodcastsByCategory(Category category) {
        List<Podcast> podcasts = podcastRepository.findByCategory(category);

        if (podcasts.isEmpty()) {
            throw new PodcastNotFoundException("No such podcast");
        }
        return podcasts;
    }

    public Podcast updatePodcast(Podcast podcast) {
        return podcastRepository.save(podcast);
    }

    public void deletePodcast(int id) {
        podcastRepository.deleteById(id);
    }
}
