package org.zchzh.systeminfo.model;

import cn.hutool.core.util.NumberUtil;
import lombok.Data;
import oshi.hardware.CentralProcessor;
import oshi.util.Util;

/**
 * @author zengchzh
 * @date 2021/6/5
 * cpu 信息
 */

@Data
public class Cpu {

    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU运行耗费的总时间
     */
    private double total;

    /**
     * CPU系统使用的时间
     */
    private double sys;

    /**
     * CPU用户使用的时间
     */
    private double used;

    /**
     * CPU当前等待的时间
     */
    private double wait;

    /**
     * CPU当前空闲的时间
     */
    private double free;

    /**
     * CPU总的使用率
     */
    private double totalRate;

    /**
     * CPU系统使用率
     */
    private double sysRate;

    /**
     * CPU用户使用率
     */
    private double usedRate;

    /**
     * CPU当前等待率
     */
    private double waitRate;

    /**
     * CPU当前空闲率
     */
    private double freeRate;

    private static final int OSHI_WAIT_SECOND = 1000;

    private static final double TOTAL_RATE = 1d;


    public Cpu(CentralProcessor processor) {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        this.cpuNum = processor.getLogicalProcessorCount();
        this.total = totalCpu;
        this.sys = cSys;
        this.used  = user;
        this.wait = iowait;
        this.free = idle;
        this.totalRate = getRate(TOTAL_RATE);
        this.sysRate = getRate(sys / total);
        this.usedRate = getRate(used / total);
        this.waitRate = getRate(wait / total);
        this.freeRate = getRate(free / total);
    }

    private double getRate(double number) {
        return NumberUtil.round(NumberUtil.mul(number, 100), 2).doubleValue();
    }
}
