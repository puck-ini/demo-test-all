package org.zchzh.mapstruct;

import com.alibaba.fastjson.JSON;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author zengchzh
 * @date 2021/8/27
 */
@Mapper
public interface DemoConvert {

    DemoConvert INSTANCE = Mappers.getMapper(DemoConvert.class);


    @Mapping(source = "demoDesc.content", target = "demoDesc")
    @Mapping(target = "defaultString", constant = "testtest")
//    @Mapping(target = "demoDescJson", expression = "java(convertJson(demoDO.getDemoDesc()))")
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd")
    DemoDTO doToDto(DemoDO demoDO);

    /**
     * 定义了这个方法会导致所有的字符串都使用这个方法
     */
//    default String convertJson(Object o) {
//        return JSON.toJSONString(o);
//    }
}
