package org.zchzh.aop.core;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public interface AopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
