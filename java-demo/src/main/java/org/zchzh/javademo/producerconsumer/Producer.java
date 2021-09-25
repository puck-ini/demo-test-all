package org.zchzh.javademo.producerconsumer;

/**
 * @author zengchzh
 * @date 2021/9/25
 */
public interface Producer {

    void produce() throws InterruptedException;
}
