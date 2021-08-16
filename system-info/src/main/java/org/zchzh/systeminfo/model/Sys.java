package org.zchzh.systeminfo.model;

import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @author zengchzh
 * @date 2021/6/5
 * 系统信息
 */

@Data
public class Sys {

    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;

    public Sys() {
        Properties props = System.getProperties();
        this.computerName = Sys.getHostName();
        this.computerIp = Sys.getHostIp();
        this.osName = props.getProperty("os.name");
        this.osArch = props.getProperty("os.arch");
        this.userDir = props.getProperty("user.dir");
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "未知";
    }
}
