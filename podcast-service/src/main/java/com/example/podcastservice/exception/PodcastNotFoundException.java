package com.example.podcastservice.exception;

public class PodcastNotFoundException extends RuntimeException {
    public PodcastNotFoundException(String message) {
        super(message);
    }
}
