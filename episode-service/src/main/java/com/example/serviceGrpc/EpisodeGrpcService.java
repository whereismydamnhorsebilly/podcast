package com.example.serviceGrpc;

import com.example.grpc.*;
import com.example.model.Episode;
import com.example.service.EpisodeService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class EpisodeGrpcService extends EpisodeServiceGrpc.EpisodeServiceImplBase {

    private final EpisodeService episodeService;

    @Override
    public void createEpisode(CreateEpisodeRequest request,
                              StreamObserver<EpisodeResponse> responseObserver) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext().build();
        PodcastServiceGrpc.PodcastServiceBlockingStub stubPodcast = PodcastServiceGrpc.newBlockingStub(channel);

        try {
            PodcastResponse podcastResponse = stubPodcast.getPodcast(GetPodcastRequest.newBuilder()
                    .setPodcastId(request.getPodcastId()).build());

            Episode episode = episodeService.createEpisode(Episode.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .podcastId(podcastResponse.getPodcastId())
                    .build());

            EpisodeResponse response = responseBuilder(episode);

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getEpisode(GetEpisodeRequest request,
                           StreamObserver<EpisodeResponse> responseObserver) {
        try {
            Episode episode = episodeService.getEpisode(request.getEpisodeId());
            EpisodeResponse response = responseBuilder(episode);

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    public EpisodeResponse responseBuilder(Episode episode) {
        return EpisodeResponse.newBuilder()
                .setEpisodeId(episode.getId())
                .setName(episode.getName())
                .setDescription(episode.getDescription())
                .setPodcastId(episode.getPodcastId())
                .build();
    }
}
