package org.zchzh.grpc.consumer;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.zchzh.grpc.api.GreeterGrpc;
import org.zchzh.grpc.api.HelloWorld;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/8/13
 */


public class HelloWorldClient {

    private String host;

    private int port;

    public HelloWorldClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ManagedChannel getChannel() {
        return ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldClient client = new HelloWorldClient("localhost", 50051);
        String user;
        user = "world0";
        client.doSync(user);
        user = "world1";
        client.doAsync(user);
        user = "world2";
        client.doFuture(user);
        System.out.println("----------------------");
        client.testSend();
        TimeUnit.SECONDS.sleep(30);
    }


    public void doSync(String name) {
        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(getChannel());
        HelloWorld.HelloRequest request = HelloWorld.HelloRequest.newBuilder().setName(name).build();
        HelloWorld.HelloReply reply;
        try {
            reply = blockingStub.sayHello(request);
            System.out.println(reply);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    public void doAsync(String name) {
        GreeterGrpc.GreeterStub stub = GreeterGrpc.newStub(getChannel());
        HelloWorld.HelloRequest request = HelloWorld.HelloRequest.newBuilder().setName(name).build();
        stub.sayHello(request, new StreamObserver<HelloWorld.HelloReply>() {
            @Override
            public void onNext(HelloWorld.HelloReply helloReply) {
                System.out.println(helloReply);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        });

    }

    public void doFuture(String name) {
        GreeterGrpc.GreeterFutureStub futureStub = GreeterGrpc.newFutureStub(getChannel());
        HelloWorld.HelloRequest request = HelloWorld.HelloRequest.newBuilder().setName(name).build();
        ListenableFuture<HelloWorld.HelloReply> future = futureStub.sayHello(request);
        future.addListener(new Runnable() {
            @Override
            public void run() {
                System.out.println("listener");
            }
        }, new ThreadPoolExecutor(12,
                24,
                3000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10),
                new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                thread.setName("test-grpc-" + r.hashCode());
                return thread;
            }
        }));

        try {
            HelloWorld.HelloReply reply = future.get();
            System.out.println(reply);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void testSend() {
        GreeterGrpc.GreeterStub stub = GreeterGrpc.newStub(getChannel());
        StreamObserver<HelloWorld.MsgRequest> requestStreamObserver = stub.send(new StreamObserver<HelloWorld.MsgResponse>() {

            @Override
            public void onNext(HelloWorld.MsgResponse msgResponse) {
                System.out.println("msgResponse = " + msgResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("throwable = " + throwable);
            }

            @Override
            public void onCompleted() {
                System.out.println("client completed");
            }
        });

        IntStream.rangeClosed(1, 10).forEach(i -> {
            requestStreamObserver.onNext(HelloWorld.MsgRequest.newBuilder().setReqId(UUID.randomUUID().toString()).build());
        });
    }
}
