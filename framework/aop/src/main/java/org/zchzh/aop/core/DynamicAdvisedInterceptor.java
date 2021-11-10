package org.zchzh.aop.core;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.zchzh.aop.advisor.TargetSource;
import org.zchzh.aop.interceptor.AopMethodInterceptor;
import org.zchzh.aop.invocation.CglibMethodInvocation;
import org.zchzh.aop.invocation.MethodInvocation;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public class DynamicAdvisedInterceptor implements MethodInterceptor {

    protected final List<AopMethodInterceptor> interceptorList;
    protected final TargetSource targetSource;

    public DynamicAdvisedInterceptor(List<AopMethodInterceptor> interceptorList, TargetSource targetSource) {
        this.interceptorList = interceptorList;
        this.targetSource = targetSource;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        MethodInvocation invocation = new CglibMethodInvocation(obj,targetSource.getTagetObject(),method, args,interceptorList,proxy);
        return invocation.proceed();
    }
}
