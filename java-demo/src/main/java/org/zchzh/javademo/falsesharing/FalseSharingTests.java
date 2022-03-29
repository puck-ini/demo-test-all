package org.zchzh.javademo.falsesharing;

/**
 * @author zengchzh
 * @date 2022/3/29
 */
public class FalseSharingTests {

    public static void main(String[] args) throws InterruptedException {
        testPointer(new Pointer());
    }

    private static void testPointer(Pointer pointer) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.a++;
            }
        }, "A");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.b++;
            }
        },"B");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(System.currentTimeMillis() - start);
        System.out.println(pointer.a + "@" + Thread.currentThread().getName());
        System.out.println(pointer.b + "@" + Thread.currentThread().getName());
    }


    static class Pointer {
        //在一个缓存行中，如果有一个线程在读取a时，会顺带把b带出
        volatile long a;  //需要volatile，保证线程间可见并避免重排序
        //方法一：放开下面这行，解决伪共享的问题，提高了性能，适用于 jdk8
        long p1, p2, p3, p4, p5, p6, p7;

        // jdk 15 改变了内存布局方式，可以用下面的方式替换使用 long 的方式解决伪共享问题
//        protected byte
//                p10, p11, p12, p13, p14, p15, p16, p17,
//                p20, p21, p22, p23, p24, p25, p26, p27,
//                p30, p31, p32, p33, p34, p35, p36, p37,
//                p40, p41, p42, p43, p44, p45, p46, p47,
//                p50, p51, p52, p53, p54, p55, p56, p57,
//                p60, p61, p62, p63, p64, p65, p66, p67,
//                p70, p71, p72, p73, p74, p75, p76, p77;
        volatile long b;   //需要volatile，保证线程间可见并避免重排序
    }


    /**
     * 方法二：将 long 类型替换成下面的 MyLong
     */
    static class MyLong {
        volatile long value;
        long p1, p2, p3, p4, p5, p6, p7;
    }

    // 方法三：使用注解 @sun.misc.Contended，使用例子：ConcurrentHashMap 种的 CounterCell 类
}
