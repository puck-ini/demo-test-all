package org.zchzh.aop.interceptor;

import org.zchzh.aop.advisor.BeforeMethodAdvice;
import org.zchzh.aop.invocation.MethodInvocation;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public class BeforeMethodAdviceInterceptor implements AopMethodInterceptor {

    private BeforeMethodAdvice advice;

    public BeforeMethodAdviceInterceptor(BeforeMethodAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        advice.before(mi.getMethod(),mi.getArguments(),mi);
        return mi.proceed();
    }
}
