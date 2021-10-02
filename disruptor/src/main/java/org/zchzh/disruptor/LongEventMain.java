package org.zchzh.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ThreadFactory;

/**
 * @author zengchzh
 * @date 2021/9/28
 */
public class LongEventMain {

    public static void main(String[] args) throws InterruptedException {

        int bufferSize = 1024;

        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setName("disruptor thread-" + r.hashCode());
                return t;
            }
        };

        ThreadFactory threadFactory1 = DaemonThreadFactory.INSTANCE;


        BlockingWaitStrategy strategy = new BlockingWaitStrategy();

        Disruptor<LongEvent> disruptor = new Disruptor<>(
                // 创建时间的工厂
                new LongEventFactory(),
                // 队列大小
                bufferSize,
                // 线程工厂
                threadFactory,
                // 生产者类型
                ProducerType.SINGLE,
                // 阻塞策略
                strategy
        );
        // 添加消费者
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, 1);
            ringBuffer.publishEvent((longEvent, l1, o) -> longEvent.set(o.getLong(0)), bb);
            Thread.sleep(1000);
        }
    }
}
