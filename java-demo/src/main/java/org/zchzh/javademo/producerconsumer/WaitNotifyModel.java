package org.zchzh.javademo.producerconsumer;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zengchzh
 * @date 2021/9/25
 */
public class WaitNotifyModel implements Model {

    private final Object LOCK = new Object();

    private final Queue<Task> buffer = new LinkedList<>();

    private final int cap;

    private final AtomicInteger increTaskNo = new AtomicInteger(0);

    public WaitNotifyModel(int cap) {
        this.cap = cap;
    }

    @Override
    public Runnable newRunnableConsumer() {
        return new ConsumerImpl();
    }

    @Override
    public Runnable newRunnableProducer() {
        return new ProducerImpl();
    }

    private class ConsumerImpl extends AbstractConsumer implements Consumer {

        @Override
        public void consume() throws InterruptedException {
            synchronized (LOCK) {
                while (buffer.size() == 0) {
                    LOCK.wait();
                }
                Task task = buffer.poll();
                if (Objects.nonNull(task)) {
                    Thread.sleep(500 + (long) (Math.random() * 500));
                    System.out.println("consume: " + task.no);
                    LOCK.notifyAll();
                }
            }
        }
    }

    private class ProducerImpl extends AbstractProducer implements Producer {

        @Override
        public void produce() throws InterruptedException {
            Thread.sleep((long) (Math.random() * 1000));
            synchronized (LOCK) {
                while (buffer.size() == cap) {
                    LOCK.wait();
                }
                Task task = new Task(increTaskNo.getAndIncrement());
                buffer.offer(task);
                System.out.println("produce: " + task.no);
                LOCK.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        int num = 5;
        Model model = new WaitNotifyModel(num);
        model.run(num);
    }
}
