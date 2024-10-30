package com.example;

import com.example.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Test {
    public static void main(String[] args) {
        ManagedChannel podcastChannel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        PodcastServiceGrpc.PodcastServiceBlockingStub podcastStub =
                PodcastServiceGrpc.newBlockingStub(podcastChannel);

        // Create episode channel
        ManagedChannel episodeChannel = ManagedChannelBuilder.forAddress("localhost", 9091)
                .usePlaintext()
                .build();

        EpisodeServiceGrpc.EpisodeServiceBlockingStub episodeStub =
                EpisodeServiceGrpc.newBlockingStub(episodeChannel);

        try {
            // 1. Create a podcast
            System.out.println("Creating podcast...");
            var podcastResponse = podcastStub.createPodcast(CreatePodcastRequest.newBuilder()
                    .setName("Test Podcast")
                    .setDescription("Test Description")
                    .setCategory("DEV")
                    .build());
            System.out.println("Created podcast with ID: " + podcastResponse.getPodcastId());

            // 2. Create an episode for that podcast
            System.out.println("\nCreating episode...");
            var episodeResponse = episodeStub.createEpisode(CreateEpisodeRequest.newBuilder()
                    .setName("Test Episode")
                    .setDescription("Test Episode Description")
                    .setPodcastId(podcastResponse.getPodcastId())
                    .build());
            System.out.println("Created episode with ID: " + episodeResponse.getEpisodeId());

            // 3. Get the episode
            System.out.println("\nGetting episode...");
            var getEpisodeResponse = episodeStub.getEpisode(GetEpisodeRequest.newBuilder()
                    .setEpisodeId(episodeResponse.getEpisodeId())
                    .build());
            System.out.println("Retrieved episode: " + getEpisodeResponse);

        } finally {
            podcastChannel.shutdown();
            episodeChannel.shutdown();
        }
    }
}
