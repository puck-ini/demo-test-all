package org.zchzh.aop.adapter;

import org.zchzh.aop.advisor.Advisor;
import org.zchzh.aop.interceptor.AopMethodInterceptor;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public interface AdviceAdapter {

    AopMethodInterceptor getInterceptor(Advisor advisor);
}
