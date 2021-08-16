package org.zchzh.systeminfo.model;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import lombok.Data;

import java.lang.management.ManagementFactory;
import java.util.Date;
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
     * JVM中已使用的内存
     */
    private double used;

    /**
     * 使用率
     */
    private double usage;

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
        long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        this.total = getM(Runtime.getRuntime().totalMemory());
        this.max = getM(Runtime.getRuntime().maxMemory());
        this.free = getM(Runtime.getRuntime().freeMemory());
        this.used = total - free;
        this.usage = NumberUtil.mul(used / total, 100);
        this.version = props.getProperty("java.version");
        this.home = props.getProperty("java.home");
        this.startTime = DateUtil.formatDateTime(new Date(startTime));
        this.runTime = DateUtil.formatBetween(DateUtil.current(false) - startTime);
    }

    /**
     * 转变成单位为 M 的数据
     * @param number 数据
     * @return 返回以 M 为单位的数据
     */
    private double getM(double number) {
        return NumberUtil.div(number, (1024 * 1024), 2);
    }
}
