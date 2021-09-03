package org.zchzh.future;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * @author zengchzh
 * @date 2021/9/3
 */
public class FutureTests {

    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(12,
            24,
            3000,
            TimeUnit.MICROSECONDS,
            new LinkedBlockingQueue<>(100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("test-" + r.hashCode());
            t.setDaemon(true);
            return t;
        }
    });

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<String> future = EXECUTOR.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("test running");
                TimeUnit.SECONDS.sleep(3);
                return "test";
            }
        });
        System.out.println("future result : " + future.get());
    }
}
