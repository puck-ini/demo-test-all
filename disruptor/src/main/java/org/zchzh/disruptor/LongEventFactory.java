package org.zchzh.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author zengchzh
 * @date 2021/9/28
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
