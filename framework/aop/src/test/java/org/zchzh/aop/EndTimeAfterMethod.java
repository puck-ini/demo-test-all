package org.zchzh.aop;

import org.zchzh.aop.advisor.AfterRunningAdvice;

import java.lang.reflect.Method;

public class EndTimeAfterMethod implements AfterRunningAdvice {

    @Override
    public Object after(Object returnVal, Method method, Object[] args, Object target) {
        long endTime = System.currentTimeMillis();
        long startTime = ThreadLocalUtils.get();
        ThreadLocalUtils.remove();
        System.out.println("方法耗时：" + (endTime - startTime) + "ms");
        return returnVal;
    }
}
