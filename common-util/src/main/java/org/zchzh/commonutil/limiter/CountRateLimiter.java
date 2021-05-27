package org.zchzh.commonutil.limiter;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zengchzh
 * @date 2021/5/24
 * 固定窗口/计数算法
 */

public class CountRateLimiter implements RateLimiter {

    /**
     * 每秒限制请求数
     */
    private long permitsPerSecond;
    /**
     * 计数器
     */
    private AtomicInteger counter = new AtomicInteger(0);

    private static final long DEFAULT_INTERVAL = 1000L;

    /**
     * 上一个窗口的开始时间
     */
    public long timestamp = System.currentTimeMillis();

    public CountRateLimiter(long permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
    }

    @Override
    public boolean limiting() {
        long now = System.currentTimeMillis();
        // 窗口内请求数量小于阈值，更新计数放行，否则拒绝请求
        if (now - timestamp < DEFAULT_INTERVAL) {
            return counter.incrementAndGet() < permitsPerSecond;
        }
        // 时间窗口过期，重置计数器和时间戳
        counter.set(0);
        timestamp = now;
        return true;
    }
}
