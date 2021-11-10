package org.zchzh.aop.adapter;

import org.zchzh.aop.advisor.Advisor;
import org.zchzh.aop.advisor.BeforeMethodAdvice;
import org.zchzh.aop.interceptor.AopMethodInterceptor;
import org.zchzh.aop.interceptor.BeforeMethodAdviceInterceptor;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public class BeforeMethodAdviceAdapter implements AdviceAdapter {
    private static final BeforeMethodAdviceAdapter INSTANTS = new BeforeMethodAdviceAdapter();

    public static BeforeMethodAdviceAdapter getInstants(){
        return INSTANTS;
    }

    @Override
    public AopMethodInterceptor getInterceptor(Advisor advisor) {
        BeforeMethodAdvice advice = (BeforeMethodAdvice) advisor.getAdvice();
        return new BeforeMethodAdviceInterceptor(advice);
    }
}
