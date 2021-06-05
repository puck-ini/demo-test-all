package org.zchzh.javademo.threadpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/6/2
 */
public class TestThreadPool implements Executor {

    BlockingQueue<Runnable> taskQueue;

    List<TestThread> threadList;


    public TestThreadPool(BlockingQueue<Runnable> taskQueue, int threadSize) {
        this.taskQueue = taskQueue;
        threadList = new ArrayList<>(threadSize);
        IntStream.rangeClosed(1, threadSize).forEach(i -> {
            TestThread testThread = new TestThread("TestThread-" + i);
            testThread.start();
            threadList.add(testThread);
        });
    }

    @Override
    public void execute(Runnable command) {
        try {
            taskQueue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    class TestThread extends Thread {

        public TestThread(String name) {
            super(name);
        }
        @Override
        public void run() {
            while (true) {
                Runnable task = null;
                try {
                    task = taskQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task != null) {
                    task.run();
                }
            }
        }
    }

    public static void main(String[] args) {
        TestThreadPool testThreadPool = new TestThreadPool(new LinkedBlockingQueue<>(10), 3);
        IntStream.rangeClosed(1, 5).forEach(i -> {
            testThreadPool.execute(() ->{
                System.out.println(Thread.currentThread().getName());
            });
        });
    }
}
