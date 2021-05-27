package org.zchzh.nionetty.promise;

import io.netty.util.concurrent.*;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author zengchzh
 * @date 2021/5/27
 */
public class PromiseTest {

    public static void main(String[] args) throws InterruptedException {

        System.out.println(System.currentTimeMillis());
        EventExecutor eventExecutor = GlobalEventExecutor.INSTANCE;
        Promise<MessageInfo> messageInfoPromise = new DefaultProgressivePromise<>(eventExecutor);
        // 监听并行顺序执行
        messageInfoPromise.addListener(new MsgListener());
        messageInfoPromise.addListener(new DescListener());
//        messageInfoPromise.addListener(new InfoListener());

        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMsg("123");
        messageInfo.setInfo("456");
        messageInfo.setDesc("789");

//                Thread.sleep(3000);
        messageInfoPromise.trySuccess(messageInfo);
//                Thread.sleep(1000);

//                Thread.sleep(3000);
        System.out.println("thread sleep 3s");

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {

            }
        }).start();

        Thread.sleep(60000);
        messageInfoPromise.addListener(new InfoListener());
//        Thread.sleep(10000);

    }


    @Data
    private static class MessageInfo{

        private String msg;

        private String info;

        private String desc;
    }

    private static class MsgListener implements GenericFutureListener<Future<MessageInfo>> {

        @Override
        public void operationComplete(Future<MessageInfo> future) throws Exception {
            if (future.isSuccess()) {
                MessageInfo messageInfo = future.getNow();
                TimeUnit.SECONDS.sleep(5);
                System.out.println(System.currentTimeMillis() + " Msg: " + messageInfo.getMsg());
            }
        }
    }

    private static class InfoListener implements GenericFutureListener<Future<MessageInfo>> {

        @Override
        public void operationComplete(Future<MessageInfo> future) throws Exception {
            if (future.isSuccess()) {
                MessageInfo messageInfo = future.getNow();
                System.out.println(System.currentTimeMillis() + " info: " + messageInfo.getInfo());
            }
        }
    }

    private static class DescListener implements GenericFutureListener<Future<MessageInfo>> {

        @Override
        public void operationComplete(Future<MessageInfo> future) throws Exception {
            if (future.isSuccess()) {
                MessageInfo messageInfo = future.getNow();
                System.out.println(System.currentTimeMillis() + " Desc: " + messageInfo.getDesc());
            }
        }
    }
}
