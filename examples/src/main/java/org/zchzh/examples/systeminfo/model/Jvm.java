package org.zchzh.examples.systeminfo.model;

import lombok.Data;

import java.util.Properties;

/**
 * @author zengchzh
 * @date 2021/6/5
 * jvm 信息
 */

@Data
public class Jvm {

    /**
     * 当前JVM占用的内存总数(M)
     */
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    /**
     * JDK启动时间
     */
    private String startTime;

    /**
     * JDK运行时间
     */
    private String runTime;

    public Jvm() {
        Properties props = System.getProperties();
        this.total = Runtime.getRuntime().totalMemory();
        this.max = Runtime.getRuntime().maxMemory();
        this.free = Runtime.getRuntime().freeMemory();
        this.version = props.getProperty("java.version");
        this.home = props.getProperty("java.home");
    }
}
