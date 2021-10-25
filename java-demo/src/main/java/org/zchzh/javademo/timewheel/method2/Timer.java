package org.zchzh.javademo.timewheel.method2;

/**
 * @author zengchzh
 * @date 2021/9/23
 */
public interface Timer {

    /**
     * 添加一个新任务
     *
     * @param timerTask
     */
    void add(TimerTask timerTask);


    /**
     * 推动指针
     *
     * @param timeout
     */
    void advanceClock(long timeout);

    /**
     * 等待执行的任务
     *
     * @return
     */
    int size();

    /**
     * 关闭服务,剩下的无法被执行
     */
    void shutdown();
}