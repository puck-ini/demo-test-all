package org.zchzh.aop.interceptor;

import org.zchzh.aop.invocation.MethodInvocation;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public interface AopMethodInterceptor {
    Object invoke(MethodInvocation mi) throws Throwable;
}
