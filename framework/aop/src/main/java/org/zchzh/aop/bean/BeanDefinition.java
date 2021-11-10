package org.zchzh.aop.bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author zengchzh
 * @date 2021/10/18
 */

@Data
@ToString
public class BeanDefinition {

    private String name;

    private String className;

    private String[] interfaceName;

    private List<ConstructorArg> constructorArgs;

    private List<PropertyArg> propertyArgs;
}
