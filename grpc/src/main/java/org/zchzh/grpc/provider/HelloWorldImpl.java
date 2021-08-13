package org.zchzh.grpc.provider;

import io.grpc.stub.StreamObserver;
import org.zchzh.grpc.api.GreeterGrpc;
import org.zchzh.grpc.api.HelloWorld;

/**
 * @author zengchzh
 * @date 2021/8/13
 */
public class HelloWorldImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloWorld.HelloRequest request, StreamObserver<HelloWorld.HelloReply> responseObserver) {
        HelloWorld.HelloReply reply = HelloWorld.HelloReply.newBuilder()
                .setMessage("hello world : " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
