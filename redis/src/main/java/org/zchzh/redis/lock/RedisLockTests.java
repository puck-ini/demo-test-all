package org.zchzh.redis.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.zchzh.redis.TestClient;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/9/2
 */
public class RedisLockTests {

    private static final String LOCK_NAME = "lock";

    private static final int NUM = 10;

    private static final CountDownLatch LATCH = new CountDownLatch(NUM);

    public static void main(String[] args) throws InterruptedException {
        RedissonClient client = TestClient.getSingleClient();
        IntStream.rangeClosed(1, NUM).forEach(i -> {
            new Thread(() -> {
                String threadName = Thread.currentThread().getName();
//                Lock lock = client.getLock(LOCK_NAME);
                RLock lock = client.getLock(LOCK_NAME);
                lock.lock();
                try {
                    System.out.println(threadName + " : " + "lock");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    LATCH.countDown();
                    System.out.println(threadName + " : " + "unlock");
                }
            }).start();
        });
        LATCH.await();
        client.shutdown();
    }

}
