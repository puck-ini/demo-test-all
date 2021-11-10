package org.zchzh.aop.utils;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public class ClassUtils {

    public static ClassLoader getDefultClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class loadClass(String className){
        try {
            return getDefultClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
