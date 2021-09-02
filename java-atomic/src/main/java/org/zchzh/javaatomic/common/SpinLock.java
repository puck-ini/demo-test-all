package org.zchzh.javaatomic.common;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zengchzh
 * @date 2021/4/13
 */
public class SpinLock {
    private AtomicReference<Thread> owner =new AtomicReference<>();

    /**
     * 将当前线程设置成锁
     */
    public void lock() {
        Thread current = Thread.currentThread();
        while(!owner.compareAndSet(null, current)) {
        }
    }

    /**
     * 释放锁
     */
    public void unlock () {
        Thread current = Thread.currentThread();
        owner.compareAndSet(current, null);
    }

    /**
     * SpinLock 是不可重入锁， 该方法会重复加锁会产生死锁
     */
    public void test1() {
        lock();
        try {
            System.out.println("test1");
            // test2 方法也使用了 SpinLock 加锁
            test2();
        }finally {
            unlock();
        }
    }

    public void test2() {
        lock();
        try {
            System.out.println("test2");
        }finally {
            unlock();
        }
    }

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        spinLock.test1();
    }
}
