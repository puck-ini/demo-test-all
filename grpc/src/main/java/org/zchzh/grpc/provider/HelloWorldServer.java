package org.zchzh.grpc.provider;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zengchzh
 * @date 2021/8/13
 */
public class HelloWorldServer {

    private int port = 50051;

    private Server server = ServerBuilder.forPort(port).addService(new HelloWorldImpl()).build();

    public void start() {
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                HelloWorldServer.this.stop();
            }
        });
        blockUntilShutdown();
    }

    public void stop() {
        if (Objects.nonNull(server)) {
            try {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void blockUntilShutdown() {
        if (server != null) {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        HelloWorldServer server = new HelloWorldServer();
        server.start();
    }
}
