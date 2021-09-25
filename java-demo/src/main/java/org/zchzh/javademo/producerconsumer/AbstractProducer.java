package org.zchzh.javademo.producerconsumer;

/**
 * @author zengchzh
 * @date 2021/9/25
 */
public abstract class AbstractProducer implements Producer, Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
