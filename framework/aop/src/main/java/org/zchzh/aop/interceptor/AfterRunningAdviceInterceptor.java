package org.zchzh.aop.interceptor;

import org.zchzh.aop.advisor.AfterRunningAdvice;
import org.zchzh.aop.invocation.MethodInvocation;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public class AfterRunningAdviceInterceptor implements AopMethodInterceptor {

    private AfterRunningAdvice advice;

    public AfterRunningAdviceInterceptor(AfterRunningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object returnVal = mi.proceed();
        advice.after(returnVal,mi.getMethod(),mi.getArguments(),mi);
        return returnVal;
    }
}
