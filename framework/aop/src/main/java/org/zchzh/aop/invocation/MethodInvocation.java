package org.zchzh.aop.invocation;

import java.lang.reflect.Method;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public interface MethodInvocation {
    Method getMethod();
    Object[] getArguments();
    Object proceed() throws Throwable;
}
