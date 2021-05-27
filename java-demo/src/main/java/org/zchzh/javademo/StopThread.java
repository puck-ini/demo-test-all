package org.zchzh.javademo;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author zengchzh
 * @date 2021/4/29
 */
public class StopThread {

    private static Boolean stop = Boolean.FALSE;


    private static synchronized void requestStop() {
        stop = Boolean.TRUE;
    }

    private static synchronized Boolean stopRequested() {
        return stop;
    }

    public static void main(String[] args) throws InterruptedException {

        /**
         * 这种方式无法停止线程
         *
         * jvm将代码转变成：
         * if (!stop) {
         *     while(true) {
         *         i++;
         *     }
         * }
         * 这种优化称作提升（ hoisting ），正是 OpenJDK Server VM 的工作
         * stop 加上 volatile 可以停止
         */
        new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("stop");
        stop = Boolean.TRUE;

//        /**
//         * 这种方式可以停止
//         */
//        new Thread(() -> {
//            int i = 0;
//            while (stopRequested()) {
//                i++;
//            }
//        }).start();
//        TimeUnit.SECONDS.sleep(1);
//        requestStop();
    }
}
