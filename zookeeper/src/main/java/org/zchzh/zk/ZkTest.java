package org.zchzh.zk;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/7/27
 */
public class ZkTest {

    private static final Integer NUM = 10;

    private static final CountDownLatch LATCH = new CountDownLatch(NUM);

    public static void main(String[] args) {
        IntStream.rangeClosed(1, NUM).forEach( i -> {
            new Thread(new TestRunnable()).start();
            LATCH.countDown();
        });
    }


    static class TestRunnable implements Runnable {

        @Override
        public void run() {
            Lock lock = new ZkLock("127.0.0.1:2181");
//            boolean isLock = lock.tryLock();
            try {
                LATCH.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
//                if (isLock) {
//                    Thread.sleep(2000);
//                    System.out.println("lock success");
//                } else {
//                    System.out.println("lock fail");
//                }

                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " : lock success");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
