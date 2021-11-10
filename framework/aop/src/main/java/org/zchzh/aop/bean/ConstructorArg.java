package org.zchzh.aop.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
@Data
@ToString
public class ConstructorArg {
    private int index;
    private String ref;
    private String name;
}
