package org.zchzh.zk;

import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/7/27
 */
public class ZkTest {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10).forEach( i -> {
            new Thread(new TestRunnable()).start();
        });
    }


    static class TestRunnable implements Runnable {

        @Override
        public void run() {
            Lock lock = new ZkLock("127.0.0.1:2181");
            boolean isLock = lock.tryLock();
            try {
                if (isLock) {
                    Thread.sleep(2000);
                    System.out.println("lock success");
                } else {
                    System.out.println("lock fail");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
