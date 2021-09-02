package org.zchzh.javaatomic.common;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/8/30
 */
public class AtomicReferenceTests {

    private static final Integer NUM = 10;

    private static CountDownLatch latch = new CountDownLatch(NUM);

    private static final AtomicReference<TestData> ATOMIC_REFERENCE = new AtomicReference<>();


    /**
     * 例子 id = 5， msg = 1234
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        TestData data = new TestData(1, "");
        ATOMIC_REFERENCE.set(data);
        IntStream.rangeClosed(1, NUM).forEach(i -> {
            new Thread(() -> {
                latch.countDown();
//                data.setId(data.getId() + 1);
//                data.setMsg(data.getMsg() + data.getId());
//                System.out.println(Thread.currentThread().getName() + " : " + data);
                TestData data1 = ATOMIC_REFERENCE.get();
                System.out.println(Thread.currentThread().getName() + " : " + ATOMIC_REFERENCE.getAndSet(new TestData(data1.getId() + 1, data1.getMsg() + data1.getId())));
            }).start();
        });
        latch.await();
    }



    static class TestData {

        private Integer id;

        private String msg;

        public TestData(Integer id, String msg) {
            this.id = id;
            this.msg = msg;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "TestData{" +
                    "id=" + id +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }
}
