package org.zchzh.commonutil.limiter;


import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/5/24
 * 固定窗口/计数算法
 */

public class CountRateLimiter implements RateLimiter {

    /**
     * 时间间隔中限制的请求数
     */
    private final long permitsPerSecond;
    /**
     * 计数器
     */
    private final AtomicInteger counter = new AtomicInteger(0);

    private final long timeInterval;

    private static final long DEFAULT_INTERVAL = 1000L;

    /**
     * 上一个窗口的开始时间
     */
    public long timestamp = System.currentTimeMillis();

    public CountRateLimiter(long permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
        this.timeInterval = DEFAULT_INTERVAL;
    }

    public CountRateLimiter(long permitsPerSecond, long timeInterval) {
        this.permitsPerSecond = permitsPerSecond;
        this.timeInterval = timeInterval;
    }

    @Override
    public boolean limiting() {
        long now = System.currentTimeMillis();
        // 窗口内请求数量小于阈值，更新计数放行，否则拒绝请求
        if (now - timestamp < timeInterval) {
            return counter.incrementAndGet() <= permitsPerSecond;
        }
        // 时间窗口过期，重置计数器和时间戳
        counter.set(0);
        timestamp = now;
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = new CountRateLimiter(5);
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Executors.defaultThreadFactory().newThread(() -> {
//                for (;;) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " : " + rateLimiter.limiting());
//                }
            }).start();
        });
    }
}
