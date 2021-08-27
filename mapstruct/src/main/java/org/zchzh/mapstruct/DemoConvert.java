package org.zchzh.mapstruct;

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
    DemoDTO doToDto(DemoDO demoDO);
}
