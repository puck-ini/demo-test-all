package org.zchzh.aop.adapter;

import org.zchzh.aop.advisor.Advisor;
import org.zchzh.aop.advisor.AfterRunningAdvice;
import org.zchzh.aop.interceptor.AfterRunningAdviceInterceptor;
import org.zchzh.aop.interceptor.AopMethodInterceptor;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public class AfterRunningAdviceAdapter implements AdviceAdapter {
    private static final AfterRunningAdviceAdapter INSTANTS = new AfterRunningAdviceAdapter();

    public static AfterRunningAdviceAdapter getInstants(){
        return INSTANTS;
    }

    @Override
    public AopMethodInterceptor getInterceptor(Advisor advisor) {
        AfterRunningAdvice advice = (AfterRunningAdvice) advisor.getAdvice();
        return new AfterRunningAdviceInterceptor(advice);
    }
}
