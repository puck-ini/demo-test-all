package org.zchzh.mapstruct;


import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zengchzh
 * @date 2021/8/27
 */

public class DemoDTO {

    private String name;

    private Integer age;

    private BigDecimal num;

    private Double value;

    private String demoEnum;

    private String demoDesc;

    private String demoDescJson;

    private String defaultString;

    private String createTime;


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

    public String getDemoEnum() {
        return demoEnum;
    }

    public void setDemoEnum(String demoEnum) {
        this.demoEnum = demoEnum;
    }

    public String getDemoDesc() {
        return demoDesc;
    }

    public void setDemoDesc(String demoDesc) {
        this.demoDesc = demoDesc;
    }

    public String getDemoDescJson() {
        return demoDescJson;
    }

    public void setDemoDescJson(String demoDescJson) {
        this.demoDescJson = demoDescJson;
    }

    public String getDefaultString() {
        return defaultString;
    }

    public void setDefaultString(String defaultString) {
        this.defaultString = defaultString;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DemoDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", num=" + num +
                ", value=" + value +
                ", demoEnum='" + demoEnum + '\'' +
                ", demoDesc='" + demoDesc + '\'' +
                ", demoDescJson='" + demoDescJson + '\'' +
                ", defaultString='" + defaultString + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
