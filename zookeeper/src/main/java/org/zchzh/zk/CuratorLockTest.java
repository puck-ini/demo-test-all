package org.zchzh.zk;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/8/2
 */
public class CuratorLockTest {

    private static final Integer NUM = 10;

    private static final CountDownLatch LATCH = new CountDownLatch(NUM);

    private static final String LOCK_NODE = "/zklock";

    public static void main(String[] args) {
//        IntStream.rangeClosed(1, NUM).forEach(i -> {
//            new Thread(new TestRunnable()).start();
//            LATCH.countDown();
//        });

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3 );
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();
        InterProcessMutex mutex = new InterProcessMutex(client, LOCK_NODE);
        try {
            mutex.acquire();
            Thread.sleep(20000);
            System.out.println(Thread.currentThread().getName() + " : lock success");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                mutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class TestRunnable implements Runnable {

        @Override
        public void run() {
            try {
                LATCH.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3 );
            CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
            client.start();
            InterProcessMutex mutex = new InterProcessMutex(client, LOCK_NODE);
            try {
                mutex.acquire();
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " : lock success");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    mutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
