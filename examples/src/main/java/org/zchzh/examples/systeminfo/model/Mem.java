package org.zchzh.examples.systeminfo.model;

import cn.hutool.core.util.NumberUtil;
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
        this.total = getM(memory.getTotal());
        this.used = getM(memory.getTotal() - memory.getAvailable());
        this.free = getM(memory.getAvailable());
    }

    /**
     * 转变成单位为 M 的数据
     * @param number 数据
     * @return 返回以 M 为单位的数据
     */
    private double getM(double number) {
        return NumberUtil.div(number, (1024 * 1024 * 1024), 2);
    }
}
