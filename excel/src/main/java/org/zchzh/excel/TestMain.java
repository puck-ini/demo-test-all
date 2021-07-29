package org.zchzh.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import lombok.Cleanup;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author zengchzh
 * @date 2021/7/29
 */
public class TestMain {

    public static void main(String[] args) throws Exception {
        testExport();
//        testImport();
    }


    public static void testExport() throws IOException {
        @Cleanup FileOutputStream fos = new FileOutputStream("C:\\Users\\zengchzh\\Desktop\\testfile\\test.xls");
        ExportParams params = new ExportParams();
//        params.setTitle("测试信息表");
        params.setSheetName("测试信息表1");
        @Cleanup Workbook workbook = ExcelExportUtil.exportExcel(params, ExcelEntity.class, createTestData());
        workbook.write(fos);
    }

    public static void testImport() throws Exception {
        @Cleanup FileInputStream fis = new FileInputStream("C:\\Users\\zengchzh\\Desktop\\testfile\\test.xls");
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
//        params.setImportFields(new String[]{"编号"});
        List<ExcelEntity> dataList = ExcelImportUtil.importExcel(fis, ExcelEntity.class, params);
        for (ExcelEntity excelEntity : dataList) {
            System.out.println(excelEntity.toString());
        }
    }

    public static List<ExcelEntity> createTestData() {
        List<ExcelEntity> dataList = new ArrayList<>();
        IntStream.rangeClosed(1, 10).forEach(i -> {
            dataList.add(ExcelEntity.builder()
                    .name("name" + i)
                    .address("address" + i)
                    .age(i)
                    .birth(new Date())
                    .info("info" + i)
                    .weight(new Random().nextDouble())
                    .phone("123789782917")
                    .build());
        });
        return dataList;
    }
}
