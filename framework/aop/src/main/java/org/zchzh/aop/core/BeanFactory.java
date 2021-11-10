package org.zchzh.aop.core;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public interface BeanFactory {
    Object getBean(String name) throws Exception;
}
