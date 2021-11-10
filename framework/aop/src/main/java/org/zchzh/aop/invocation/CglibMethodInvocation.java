package org.zchzh.aop.invocation;

import net.sf.cglib.proxy.MethodProxy;
import org.zchzh.aop.interceptor.AopMethodInterceptor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public class CglibMethodInvocation extends ReflectioveMethodeInvocation {

    private MethodProxy methodProxy;

    public CglibMethodInvocation(Object proxy,
                                 Object target,
                                 Method method,
                                 Object[] arguments,
                                 List<AopMethodInterceptor> interceptorList,
                                 MethodProxy methodProxy) {
        super(proxy, target, method, arguments, interceptorList);
        this.methodProxy = methodProxy;
    }

    @Override
    protected Object invokeOriginal() throws Throwable {
        return methodProxy.invoke(target,arguments);
    }
}
