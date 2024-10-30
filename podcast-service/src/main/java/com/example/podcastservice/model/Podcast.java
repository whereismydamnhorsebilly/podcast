package com.example.podcastservice.model;

import com.example.podcastservice.utils.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "podcast")
public class Podcast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "podcast_id")
    private int id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;
}
