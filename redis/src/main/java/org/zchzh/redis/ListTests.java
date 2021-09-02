package org.zchzh.redis;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zengchzh
 * @date 2021/9/2
 */
public class ListTests {

    private static BlockingQueue<String> QUEUE = new LinkedBlockingQueue<>(100);

    private static final Random RANDOM = new Random();
    public static void main(String[] args) throws InterruptedException {
//        RedissonClient client = TestClient.getSingleClient();
//        QUEUE = client.getBlockingQueue("test-list");
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    int num = RANDOM.nextInt(100);
                    QUEUE.put(String.valueOf(num));
                    System.out.println("put: " + num);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                String msg = QUEUE.poll();
                if (Objects.nonNull(msg)) {
                    System.out.println("poll: " + msg);
                }
//
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
        t1.setName("put-thread");
        t2.setName("poll-thread");
        t1.start();
        t2.start();
    }

}
