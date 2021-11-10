package org.zchzh.aop.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
@Data
@ToString
public class PropertyArg {

    private String name;

    private String value;

    private String typeName;

    private String ref;
}
