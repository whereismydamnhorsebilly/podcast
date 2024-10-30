package com.example.podcastservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PodcastDto {

    private int id;

    private String name;

    private String description;

    private String category;
}
