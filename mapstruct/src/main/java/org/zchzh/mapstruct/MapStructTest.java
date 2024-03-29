package org.zchzh.mapstruct;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * @author zengchzh
 * @date 2021/8/27
 */
public class MapStructTest {

    public static void main(String[] args) {
        DemoDO demoDO = new DemoDO();
        demoDO.setAge(12);
        demoDO.setDemoEnum(DemoEnum.A);
        demoDO.setName("123weq");
        demoDO.setNum(new BigDecimal("123.9077"));
        demoDO.setValue(123.321);
        demoDO.setCreateTime(new Date());
        demoDO.setMsg1("msg1");
        demoDO.setMsg2("msg2");
        DemoDesc demoDesc = new DemoDesc();
        demoDesc.setContent("123&*(&(*");
        demoDesc.setLen(123);
        demoDO.setDemoDesc(demoDesc);
        System.out.println(demoDO);
        System.out.println(DemoConvert.INSTANCE.doToDto(demoDO));
    }
}
