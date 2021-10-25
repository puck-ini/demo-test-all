package org.zchzh.javademo.timewheel.method2;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zengchzh
 * @date 2021/9/23
 */
@Data
@Slf4j
class TimerTask implements Runnable {
    /**
     * 延时时间
     */
    private long delayMs;
    /**
     * 任务所在的entry
     */
    private TimerTaskEntry timerTaskEntry;

    private String desc;

    public TimerTask(String desc, long delayMs) {
        this.desc = desc;
        this.delayMs = delayMs;
        this.timerTaskEntry = null;
    }

    public synchronized void setTimerTaskEntry(TimerTaskEntry entry) {
        // 如果这个timetask已经被一个已存在的TimerTaskEntry持有,先移除一个
        if (timerTaskEntry != null && timerTaskEntry != entry) {
            timerTaskEntry.remove();
        }
        timerTaskEntry = entry;
    }

    public TimerTaskEntry getTimerTaskEntry() {
        return timerTaskEntry;
    }

    @Override
    public void run() {
        log.info("============={}任务执行", desc);
    }
}
