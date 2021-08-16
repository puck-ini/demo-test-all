package org.zchzh.systeminfo.model;

import cn.hutool.core.util.NumberUtil;
import lombok.Data;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengchzh
 * @date 2021/6/5
 * 系统文件信息
 */

@Data
public class SysFiles {

    List<SysFile> sysFiles;

    public SysFiles(OperatingSystem os) {
        sysFiles = new ArrayList<>();
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFile sysFile = new SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(NumberUtil.mul(NumberUtil.div(used, total, 4), 100));
            sysFiles.add(sysFile);
        }
    }
    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    private String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }


    @Data
    static class SysFile {
        /**
         * 盘符路径
         */
        private String dirName;

        /**
         * 盘符类型
         */
        private String sysTypeName;

        /**
         * 文件类型
         */
        private String typeName;

        /**
         * 总大小
         */
        private String total;

        /**
         * 剩余大小
         */
        private String free;

        /**
         * 已经使用量
         */
        private String used;

        /**
         * 资源的使用率
         */
        private double usage;
    }
}
