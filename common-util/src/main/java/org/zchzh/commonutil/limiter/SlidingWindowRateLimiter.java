package org.zchzh.commonutil.limiter;



import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/5/24
 *
 * 滑动窗口算法
 */

public class SlidingWindowRateLimiter implements RateLimiter {
    /**
     * 每分钟限制请求数
     */
    private final long permitsPerMinute;

    /**
     * 计数器，key 为当前窗口的开始时间值（秒），value 为当前窗口的计数
     */
    private final TreeMap<Long, Integer> counters = new TreeMap<>();

    /**
     * 一段大小，单位秒
     */
    private final long segmentSize;

    /**
     * 窗口段数
     */
    private final long segmentNum;

    public SlidingWindowRateLimiter(long permitsPerMinute) {
        this.permitsPerMinute = permitsPerMinute;
        this.segmentSize = 10;
        this.segmentNum = 5;
    }

    public SlidingWindowRateLimiter(long permitsPerMinute, long segmentSize, long segmentNum) {
        this.permitsPerMinute = permitsPerMinute;
        this.segmentSize = segmentSize;
        this.segmentNum = segmentNum;
    }

    @Override
    public synchronized boolean limiting() {
        // 获取当前时间的所在的子窗口值
        long currentWindowTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) / segmentSize * segmentSize;

        // 获取当前窗口的请求总量
        int currentWindowCount = getCurrentWindowCount(currentWindowTime);
        if (currentWindowCount >= permitsPerMinute) {
            return false;
        }
        counters.merge(currentWindowTime, 1, Integer::sum);
        return true;
    }

    private int getCurrentWindowCount(long currentWindowTime) {
        System.out.println("counters size = " + counters.size());
        // 计算出窗口的开始位置时间
        long startTime = currentWindowTime - segmentSize * segmentNum;
//        System.out.println("currentWindowTime = " + currentWindowTime + ", startTime = " + startTime);
        int result = 0;

        // 遍历当前存储的计数器，删除无效的子窗口计数器，并累加当前窗口中的所有计数器之和
        Iterator<Map.Entry<Long, Integer>> iterator = counters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Integer> entry = iterator.next();
//            System.out.println("entry key" + entry.getKey());
            if (entry.getKey() <= startTime) {
                iterator.remove();
            } else {
                result += entry.getValue();
            }
        }
        System.out.println("result = " + result);
        return result;
    }

    public static void main(String[] args) {
        RateLimiter rateLimiter = new SlidingWindowRateLimiter(100, 1, 10);
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Executors.defaultThreadFactory().newThread(() -> {
                for (;;) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    rateLimiter.limiting();
                    System.out.println(Thread.currentThread().getName() + " : " + rateLimiter.limiting());
                }
            }).start();
        });
    }
}
