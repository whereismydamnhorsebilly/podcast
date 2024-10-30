package com.example.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.logging.Logger;

public class PodcastServiceClient {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final PodcastServiceGrpc.PodcastServiceBlockingStub stub;
    private final ManagedChannel channel;

    public PodcastServiceClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        stub = PodcastServiceGrpc.newBlockingStub(channel);
    }

    public PodcastResponse createPodcast(String name, String description, String category) {
        CreatePodcastRequest request = CreatePodcastRequest.newBuilder()
                .setName(name)
                .setDescription(description)
                .setCategory(category)
                .build();
        try{
            return stub.createPodcast(request);
        } catch (StatusRuntimeException e) {
            logger.info("Oops, something went wrong");
            throw e;
        }
    }

    public PodcastResponse getPodcast(int podcastId) {
        GetPodcastRequest request = GetPodcastRequest.newBuilder()
                .setPodcastId(podcastId).build();
        try {
            return stub.getPodcast(request);
        } catch (StatusRuntimeException e) {
            logger.info("Oops, something went wrong");
            throw e;
        }
    }

    public ListOfPodcastsResponse getPodcastsByCategory(String category) {
        GetPodcastsByCategoryRequest request = GetPodcastsByCategoryRequest.newBuilder().
                setCategory(category)
                .build();
        try {
            return stub.getPodcastsByCategory(request);
        } catch (StatusRuntimeException e) {
            logger.info("Oops, i did it again");
            throw e;
        }
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown();
    }

}
