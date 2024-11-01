package com.example.service;

import com.example.dto.SimpleStatDto;
import com.example.model.SimpleStat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatService {

    void updatePodcastCount();

    void updateEpisodeCount();

    long getPodcastCount();

    long getEpisodeCount();

    SimpleStat createSimpleStat(SimpleStatDto simpleStatDto);

    List<SimpleStat> getAllStats();

}
