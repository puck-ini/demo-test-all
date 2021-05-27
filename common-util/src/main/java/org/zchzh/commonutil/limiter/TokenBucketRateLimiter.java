package org.zchzh.commonutil.limiter;


import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zengchzh
 * @date 2021/5/25
 */
public class TokenBucketRateLimiter implements RateLimiter{

    /**
     * 令牌桶的容量，限流器允许的最大突发流量
     */
    private final long catacity;
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
    private AtomicLong currentTokens;

    public TokenBucketRateLimiter(long generatedPerSeconds, int catacity) {
        this.generatedPerSeconds = generatedPerSeconds;
        this.catacity = catacity;
        this.currentTokens = new AtomicLong(0);
    }

    @Override
    public boolean limiting() {
        long now = System.currentTimeMillis();
        if (now - lastTokenTime >= 1000) {
            long newPermits = (now - lastTokenTime) / 1000 * generatedPerSeconds;
            currentTokens.set(Math.min(currentTokens.get() + newPermits, catacity));
            lastTokenTime = now;
        }
        if (currentTokens.get() > 0) {
            currentTokens.decrementAndGet();
            return true;
        }
        return false;
    }
}
