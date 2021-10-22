package org.zchzh.commonutil.limiter;


import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/5/25
 *
 * 令牌桶算法
 */
public class TokenBucketRateLimiter implements RateLimiter{

    /**
     * 令牌桶的容量，限流器允许的最大突发流量
     */
    private final long capacity;
    /**
     * 令牌发放速率
     */
    private final long generatedPerSeconds;
    /**
     * 最后一个令牌发放的时间
     */
    private long lastTokenTime = System.currentTimeMillis();
    /**
     * 当前令牌数量
     */
    private long currentTokens = 0;

    private final long interval;

    public TokenBucketRateLimiter(long generatedPerSeconds, int capacity) {
        this.generatedPerSeconds = generatedPerSeconds;
        this.capacity = capacity;
        this.interval = 1000;
    }

    public TokenBucketRateLimiter(long generatedPerSeconds, long interval, TimeUnit unit, int capacity) {
        this.generatedPerSeconds = generatedPerSeconds;
        this.capacity = capacity;
        this.interval = unit.toMillis(interval);
    }

    @Override
    public synchronized boolean limiting() {
        long now = System.currentTimeMillis();

        // 请求的同时放入令牌，而不是使用另一个线程按时放入令牌
        if (now - lastTokenTime >= interval) {
            long newPermits = (now - lastTokenTime) / interval * generatedPerSeconds;
            currentTokens = Math.min(currentTokens + newPermits, capacity);
            lastTokenTime = now;
        }
        if (currentTokens > 0) {
            System.out.println(--currentTokens);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        RateLimiter limiter = new TokenBucketRateLimiter(1,100, TimeUnit.MILLISECONDS, 100);
        IntStream.rangeClosed(1, 5).forEach(i -> {
            new Thread(() -> {
                for (;;) {
                    boolean success = limiter.limiting();
//                    System.out.println(Thread.currentThread().getName() + ": " + success);
                    if (!success) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        });
    }
}
