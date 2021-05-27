package org.zchzh.commonutil.limiter;

/**
 * @author zengchzh
 * @date 2021/5/24
 */
public interface RateLimiter {

    /**
     * 限流
     * @return true 通过，false 限流
     */
    boolean limiting();
}
