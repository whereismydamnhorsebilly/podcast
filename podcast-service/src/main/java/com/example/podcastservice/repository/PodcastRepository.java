package com.example.podcastservice.repository;

import com.example.podcastservice.model.Podcast;
import com.example.podcastservice.utils.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, Integer> {

    List<Podcast> findByCategory(Category category);
}
