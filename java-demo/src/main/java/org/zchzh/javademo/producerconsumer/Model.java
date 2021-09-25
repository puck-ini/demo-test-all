package org.zchzh.javademo.producerconsumer;

import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/9/25
 */
public interface Model {

    Runnable newRunnableConsumer();

    Runnable newRunnableProducer();

    default void run(int cap) {
        IntStream.rangeClosed(1, cap).forEach(i -> {
            new Thread(this.newRunnableConsumer()).start();
        });

        IntStream.rangeClosed(1, cap).forEach(i -> {
            new Thread(this.newRunnableProducer()).start();
        });
    }
}
