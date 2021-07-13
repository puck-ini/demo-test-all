package org.zchzh.javademo.eventlistener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zengchzh
 * @date 2021/7/13
 */
public class DemoEventPulisher {

    private List<DemoListener> listenerList = new ArrayList<>();

    public void monitor() {
        DemoEvent demoEvent = new DemoEvent(this, UUID.randomUUID().toString());
        pulishEvent(demoEvent);
    }


    protected void pulishEvent(DemoEvent event) {
        List<DemoListener> copyListenerList = new ArrayList<>(listenerList);
        for (DemoListener listener : copyListenerList) {
            listener.listener(event);
        }
    }

    public void addListener(DemoListener listener) {
        listenerList.add(listener);
    }

    public void removeListener(DemoListener listener) {
        listenerList.remove(listener);
    }

    public void removeAll() {
        listenerList.clear();
    }

    public static void main(String[] args) {
        DemoEventPulisher pulisher = new DemoEventPulisher();
        pulisher.addListener(new DemoListener());
        pulisher.monitor();
    }
}
