package org.zchzh.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author zengchzh
 * @date 2021/9/28
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println("longEvent = " + longEvent.getValue());
    }
}
