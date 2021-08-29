package org.zchzh.javaagent;

/**
 * @author zengchzh
 * @date 2021/8/27
 */
public class TestMain {

    public static void main(String[] args) {
        System.out.println("main start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end");
    }
}
