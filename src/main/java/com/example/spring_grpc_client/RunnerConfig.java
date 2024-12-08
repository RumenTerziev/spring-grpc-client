package com.example.spring_grpc_client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import src.main.proto.DataStreamerGrpc;
import src.main.proto.StreamRequest;
import src.main.proto.StreamResponse;

import java.util.Iterator;

@Configuration
public class RunnerConfig {

    @Bean
    public ApplicationRunner clientRunner(@GrpcClient("dataStreamerService") DataStreamerGrpc.DataStreamerBlockingStub dataStreamerBlockingStub) {
        return runner -> {

            Iterator<StreamResponse> responses = dataStreamerBlockingStub.streamData(StreamRequest.newBuilder()
                    .setData("example")
                    .build());

            while (responses.hasNext()) {
                StreamResponse streamResponse = responses.next();
                System.out.println(streamResponse.toString());
            }
        };
    }
}
