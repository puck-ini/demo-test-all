package org.zchzh.disruptor;

/**
 * @author zengchzh
 * @date 2021/9/28
 */
public class LongEvent {

    private long value;

    public void set(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
