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

    /**
     * 测试数据脱敏
     */
    @Excel(name = "手机号", desensitizationRule = "3_4")
    private String phone;

    @Excel(name = "年龄")
    private Integer age;

    @Excel(name = "体重")
    private Double weight;

    @Excel(name = "出生日期", format = "yyyy-MM-dd HH:mm:ss")
    private Date birth;

    /**
     * 测试下拉框，读取时无法读出字符串
     */
    @Excel(name = "状态1", replace = {"CREATE_0", "SAVE_1", "DELETE_2"}, addressList = true)
    private String status1;

    /**
     * 测试枚举，读取时是null
     */
    @Excel(name = "状态2", replace = {"CREATE_0", "SAVE_1", "DELETE_2"}, addressList = true)
    private Status status2;


}
