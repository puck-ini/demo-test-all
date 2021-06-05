package org.zchzh.examples.systeminfo.model;

import lombok.Data;
import oshi.hardware.GlobalMemory;

/**
 * @author zengchzh
 * @date 2021/6/5
 * 内存信息
 */
@Data
public class Mem {

    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public Mem(GlobalMemory memory) {
        this.total = memory.getTotal();
        this.used = memory.getTotal() - memory.getAvailable();
        this.free = memory.getAvailable();
    }
}
