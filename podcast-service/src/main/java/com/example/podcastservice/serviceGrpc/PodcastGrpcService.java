package com.example.podcastservice.serviceGrpc;

import com.example.grpc.*;
import com.example.podcastservice.exception.PodcastNotFoundException;
import com.example.podcastservice.model.Podcast;
import com.example.podcastservice.service.PodcastService;
import com.example.podcastservice.utils.Category;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class PodcastGrpcService extends PodcastServiceGrpc.PodcastServiceImplBase {

    private final PodcastService podcastService;

    @Override
    public void createPodcast(CreatePodcastRequest request,
                              StreamObserver<PodcastResponse> responseObserver) {

        Podcast podcast = podcastService.createPodcast(Podcast.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(Category.valueOf(request.getCategory()))
                .build());

        PodcastResponse response = responseBuilder(podcast);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPodcast(GetPodcastRequest request,
                           StreamObserver<PodcastResponse> responseObserver) {
        try {
            Podcast podcast = podcastService.getPodcast(request.getPodcastId());
            PodcastResponse response = responseBuilder(podcast);

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (PodcastNotFoundException e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getPodcastsByCategory(GetPodcastsByCategoryRequest request,
                                      StreamObserver<ListOfPodcastsResponse> responseObserver) {
        try {
            List<Podcast> podcasts = podcastService.getPodcastsByCategory(Category.valueOf(request.getCategory()));
            ListOfPodcastsResponse response = ListOfPodcastsResponse.newBuilder()
                    .addAllPodcasts(podcasts.stream()
                            .map(this::responseBuilder)
                            .toList())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (PodcastNotFoundException e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    public PodcastResponse responseBuilder(Podcast podcast) {
        return PodcastResponse.newBuilder()
                .setPodcastId(podcast.getId())
                .setName(podcast.getName())
                .setDescription(podcast.getDescription())
                .setCategory(podcast.getCategory().toString())
                .build();
    }

}
