package org.zchzh.aop.core;

import lombok.Data;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.zchzh.aop.advisor.AdvisedSupport;
import org.zchzh.aop.utils.ClassUtils;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
@Data
public class CglibAopProxy implements AopProxy {

    private AdvisedSupport advised;

    private Object[] constructorArgs;

    private Class<?>[] constructorArgTypes;

    public CglibAopProxy(AdvisedSupport config){
        this.advised = config;
    }



    @Override
    public Object getProxy() {
        return getProxy(null);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {

        Class<?> rootClass = advised.getTargetSource().getTagetClass();

        if(classLoader == null){
            classLoader = ClassUtils.getDefultClassLoader();
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(rootClass.getSuperclass());
        //增加拦截器的核心方法
        Callback callbacks = getCallBack(advised);
        enhancer.setCallback(callbacks);
        enhancer.setClassLoader(classLoader);
        if(constructorArgs != null && constructorArgs.length > 0){
            return enhancer.create(constructorArgTypes,constructorArgs);
        }

        return enhancer.create();
    }
    private Callback getCallBack(AdvisedSupport advised) {
        return new DynamicAdvisedInterceptor(advised.getList(),advised.getTargetSource());
    }
}
