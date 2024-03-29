package org.zchzh.mapstruct;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zengchzh
 * @date 2021/8/27
 */

public class DemoDO extends BaseDO {

    private String name;

    private Integer age;

    private BigDecimal num;

    private Double value;

    private DemoEnum demoEnum;

    private DemoDesc demoDesc;

    private Date createTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public DemoEnum getDemoEnum() {
        return demoEnum;
    }

    public void setDemoEnum(DemoEnum demoEnum) {
        this.demoEnum = demoEnum;
    }

    public DemoDesc getDemoDesc() {
        return demoDesc;
    }

    public void setDemoDesc(DemoDesc demoDesc) {
        this.demoDesc = demoDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DemoDO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", num=" + num +
                ", value=" + value +
                ", demoEnum=" + demoEnum +
                ", demoDesc=" + demoDesc +
                ", createTime=" + createTime +
                '}';
    }
}
