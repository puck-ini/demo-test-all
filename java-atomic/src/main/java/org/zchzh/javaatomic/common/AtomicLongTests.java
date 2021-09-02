package org.zchzh.javaatomic.common;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/8/30
 */
public class AtomicLongTests {

    private static final Integer NUM = 10;

    private static CountDownLatch latch = new CountDownLatch(NUM);

    private static AtomicLong atomicLong = new AtomicLong(0);

    private static Long count = 0L;

    public static void main(String[] args) throws InterruptedException {
        IntStream.rangeClosed(1, NUM).forEach(i -> {
            latch.countDown();
            new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println(Thread.currentThread().getName() + " : " + (++count));
                System.out.println(Thread.currentThread().getName() + " : " + atomicLong.incrementAndGet());
            }).start();
        });
        TimeUnit.SECONDS.sleep(3);
        System.out.println(count);
        System.out.println(atomicLong.get());
    }
}
