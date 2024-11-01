package com.example;

import com.example.serviceGrpc.EpisodeGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class EpisodeServiceApplication
{
    public static void main( String[] args ) throws IOException, InterruptedException {

//        PodcastServiceClient client = new PodcastServiceClient();
//
//        PodcastResponse response = client
//                .createPodcast("Test Podcast 1", "Some description 1", "DEV");
//
//        System.out.println("New podcast created: " + response);
//
//        PodcastResponse responseToGet = client.getPodcast(response.getPodcastId());
//
//        System.out.println("Podcast to get: " + responseToGet);

        ApplicationContext context = SpringApplication.run(EpisodeServiceApplication.class, args);
        EpisodeGrpcService episodeGrpcService = context.getBean(EpisodeGrpcService.class);

        Server server = ServerBuilder.forPort(9091)
                .addService(episodeGrpcService)
                .build();

        server.start();
        System.out.println("Server started, listening on " + server.getPort());
        server.awaitTermination();

    }
}
