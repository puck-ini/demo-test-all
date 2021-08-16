package org.zchzh.systeminfo;

import lombok.Data;
import org.zchzh.systeminfo.model.*;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * @author zengchzh
 * @date 2021/6/5
 */

@Data
public class Server {

    /**
     * CPU相关信息
     */
    private Cpu cpu;

    /**
     * 內存相关信息
     */
    private Mem mem;

    /**
     * JVM相关信息
     */
    private Jvm jvm;

    /**
     * 服务器相关信息
     */
    private Sys sys;

    /**
     * 磁盘相关信息
     */
    private SysFiles sysFiles;

    public Server() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        this.cpu = new Cpu(hal.getProcessor());
        this.mem = new Mem(hal.getMemory());
        this.jvm = new Jvm();
        this.sys = new Sys();
        this.sysFiles = new SysFiles(si.getOperatingSystem());
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Server server = new Server();

        System.out.println(server);

        System.out.println(System.currentTimeMillis() - start);
    }
}
