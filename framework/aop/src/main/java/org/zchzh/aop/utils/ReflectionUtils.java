package org.zchzh.aop.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public class ReflectionUtils {

    public static Object invokeMethodUseReflection(Object target, Method method, Object[] args){

        method.setAccessible(true);
        try {
            return method.invoke(target,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
