package org.zchzh.grpc.provider;

import io.grpc.stub.StreamObserver;
import org.zchzh.grpc.api.GreeterGrpc;
import org.zchzh.grpc.api.HelloWorld;

import java.util.UUID;

/**
 * @author zengchzh
 * @date 2021/8/13
 */
public class HelloWorldImpl extends GreeterGrpc.GreeterImplBase {

    /**
     * 可以用 map 保存
     */
    StreamObserver<HelloWorld.MsgResponse> responseObserver;

    @Override
    public void sayHello(HelloWorld.HelloRequest request, StreamObserver<HelloWorld.HelloReply> responseObserver) {
        HelloWorld.HelloReply reply = HelloWorld.HelloReply.newBuilder()
                .setMessage("hello world : " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloWorld.MsgRequest> send(StreamObserver<HelloWorld.MsgResponse> responseObserver) {
        this.responseObserver = responseObserver;
        return new StreamObserver<HelloWorld.MsgRequest>() {
            @Override
            public void onNext(HelloWorld.MsgRequest msgRequest) {
                System.out.println("msgRequest = " + msgRequest);
                HelloWorldImpl.this.responseObserver.onNext(HelloWorld.MsgResponse.newBuilder()
                        .setResId(UUID.randomUUID().toString())
                        .build());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("throwable = " + throwable);
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
                HelloWorldImpl.this.responseObserver.onCompleted();
            }
        };
    }


    public void test() {
        this.responseObserver.onNext(HelloWorld.MsgResponse.newBuilder().setResId("test123").build());
    }

}
