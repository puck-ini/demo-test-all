package org.zchzh.javademo.timewheel.method2;

import lombok.Data;

/**
 * @author zengchzh
 * @date 2021/9/23
 */
@Data
class TimerTaskEntry implements Comparable<TimerTaskEntry> {
    private TimerTask timerTask;
    private long expireMs;
    volatile TimerTaskList timedTaskList;
    TimerTaskEntry next;
    TimerTaskEntry prev;

    public TimerTaskEntry(TimerTask timedTask, long expireMs) {
        this.timerTask = timedTask;
        this.expireMs = expireMs;
        this.next = null;
        this.prev = null;
    }

    void remove() {
        TimerTaskList currentList = timedTaskList;
        while (currentList != null) {
            currentList.remove(this);
            currentList = timedTaskList;
        }
    }

    @Override
    public int compareTo(TimerTaskEntry o) {
        return ((int) (this.expireMs - o.expireMs));
    }
}
