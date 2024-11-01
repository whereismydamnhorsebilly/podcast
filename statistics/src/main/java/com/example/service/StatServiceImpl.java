package com.example.service;

import com.example.dto.SimpleStatDto;
import com.example.model.SimpleStat;
import com.example.repository.StatRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;

    private long podcastCount = 0;
    private long episodeCount = 0;

    public void updatePodcastCount() {
        podcastCount++;
    }
    public void updateEpisodeCount() {
        episodeCount++;
    }

    public SimpleStat createSimpleStat(SimpleStatDto simpleStatDto) {
        SimpleStat simpleStat = SimpleStat.builder()
                .podcastId(simpleStatDto.getPodcastId())
                .podcastRegister(LocalDateTime.now())
                .build();
        return statRepository.save(simpleStat);
    }

    public List<SimpleStat> getAllStats() {
        return statRepository.findAll();
    }
}
