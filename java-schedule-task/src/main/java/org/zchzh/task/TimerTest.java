package org.zchzh.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author zengchzh
 * @date 2021/11/30
 */
public class TimerTest {


    @Test
    public void testTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("doSomething");
            }
            // 延迟2秒运行，间隔1秒
        },2000,1000);
    }


    @Test
    public void testScheduledExecutor() {
        ScheduledThreadPoolExecutor  executor = new ScheduledThreadPoolExecutor(3, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("test: " + LocalDateTime.now());
            }
            // 延迟2秒运行，间隔1秒
        }, 2, 1, TimeUnit.SECONDS);
    }

    @AfterEach
    public void sleep() {
        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
