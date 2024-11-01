package com.example.podcastservice;

import com.example.podcastservice.serviceGrpc.PodcastGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class PodcastServiceApplication {

    public static void main(String[] args) throws InterruptedException, IOException {

        ApplicationContext context = SpringApplication.run(PodcastServiceApplication.class, args);
        PodcastGrpcService podcastGrpcService = context.getBean(PodcastGrpcService.class);

        Server server = ServerBuilder.forPort(9090)
                .addService(podcastGrpcService)
                .build();

        server.start();
        System.out.println("Server started, listening on " + server.getPort());
        server.awaitTermination();
    }

}
