package org.zchzh.javademo.producerconsumer;

/**
 * @author zengchzh
 * @date 2021/9/25
 */
public abstract class AbstractConsumer implements Consumer, Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
