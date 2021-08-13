package org.zchzh.grpc.consumer;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.zchzh.grpc.api.GreeterGrpc;
import org.zchzh.grpc.api.HelloWorld;

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

    public static void main(String[] args) {
        HelloWorldClient client = new HelloWorldClient("localhost", 50051);
        String user;
        user = "world0";
        client.doSync(user);
    }


    public void doSync(String name) {
        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(getChannel());
        HelloWorld.HelloRequest request = HelloWorld.HelloRequest.newBuilder().setName(name).build();
        HelloWorld.HelloReply reply;
        try {
            reply = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    public void doAsync(String name) {
//        GreeterGrpc.GreeterStub stub = GreeterGrpc.GreeterStub.newStub(getChannel());

    }

    public void doFuture(String name) {
//        GreeterGrpc.GreeterFutureStub futureStub = GreeterGrpc.GreeterFutureStub.newStub(getChannel());
    }
}
