package org.zchzh.aop.invocation;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public interface ProxyMethodInvocation extends MethodInvocation {
    Object getProxy();
}
