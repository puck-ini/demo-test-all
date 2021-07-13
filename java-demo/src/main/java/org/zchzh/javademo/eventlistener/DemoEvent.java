package org.zchzh.javademo.eventlistener;

import java.util.EventObject;

/**
 * @author zengchzh
 * @date 2021/7/13
 */
public class DemoEvent extends EventObject {

    private String msg;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DemoEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "DemoEvent{" +
                "msg='" + msg + '\'' +
                ", source=" + source +
                '}';
    }
}
