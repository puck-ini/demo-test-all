package org.zchzh.javademo.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zengchzh
 * @date 2021/9/25
 */
public class BlockingQueueModel implements Model {

    private final BlockingQueue<Task> queue;

    private final AtomicInteger increTaskNo = new AtomicInteger(0);

    public BlockingQueueModel(int cap) {
        this.queue = new LinkedBlockingQueue<>(cap);
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
            Task task = queue.take();
            Thread.sleep(500 + (long) (Math.random() * 500));
            System.out.println("consume: " + task.no);
        }
    }

    private class ProducerImpl extends AbstractProducer implements Producer {

        @Override
        public void produce() throws InterruptedException {
            Thread.sleep((long) (Math.random() * 1000));
            Task task = new Task(increTaskNo.getAndIncrement());
            System.out.println("producer: " + task.no);
            queue.put(task);
        }
    }

    public static void main(String[] args) {
        int num = 5;
        Model model = new BlockingQueueModel(num);
        model.run(num);
    }
}
