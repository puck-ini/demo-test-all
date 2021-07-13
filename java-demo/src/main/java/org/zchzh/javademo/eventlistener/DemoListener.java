package org.zchzh.javademo.eventlistener;

import java.util.EventListener;

/**
 * @author zengchzh
 * @date 2021/7/13
 */
public class DemoListener implements EventListener {

    public void listener(DemoEvent demoEvent) {
        System.out.println("demoEvent = " + demoEvent);
    }
}
