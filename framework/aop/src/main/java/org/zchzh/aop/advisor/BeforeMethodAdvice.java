package org.zchzh.aop.advisor;

import java.lang.reflect.Method;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public interface BeforeMethodAdvice extends Advice {
    void before(Method method, Object[] args, Object target);
}
