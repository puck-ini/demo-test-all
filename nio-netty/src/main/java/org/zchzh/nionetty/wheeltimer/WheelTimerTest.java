package org.zchzh.nionetty.wheeltimer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * @author zengchzh
 * @date 2021/5/27
 */
public class WheelTimerTest {

    public static void main(String[] args) {
        long interval = 1000L;
        Timer timer = new HashedWheelTimer();
        TimerTask task = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("timeout = " + timeout + "----- " + new Date());
                // 不继续注册任务只会执行一次
                timer.newTimeout(this, interval, TimeUnit.MILLISECONDS);
            }
        };
        timer.newTimeout(task, interval, TimeUnit.MILLISECONDS);

    }
}
