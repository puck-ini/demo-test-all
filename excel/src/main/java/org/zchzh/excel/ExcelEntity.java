package org.zchzh.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zengchzh
 * @date 2021/7/29
 */

@Data
@ExcelTarget("excel_entity")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelEntity implements Serializable {

    @Excel(name = "名字")
    private String name;

    @Excel(name = "信息")
    private String info;

    @Excel(name = "地址")
    private String address;

    @Excel(name = "年龄")
    private Integer age;

    @Excel(name = "体重")
    private Double weight;

    @Excel(name = "出生日期", format = "yyyy-MM-dd HH:mm:ss")
    private Date birth;


}
