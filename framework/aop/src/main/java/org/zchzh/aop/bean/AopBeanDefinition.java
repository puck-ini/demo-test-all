package org.zchzh.aop.bean;

import lombok.Data;

import java.util.List;

/**
 * @author zengchzh
 * @date 2021/10/18
 */

@Data
public class AopBeanDefinition extends BeanDefinition {

    private String target;

    private List<String> interceptorNames;
}
