package org.zchzh.aop;

import org.zchzh.aop.core.AopApplicationContext;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
public class TestMain {

    public static void main(String[] args) throws Exception {

        AopApplicationContext aopApplictionContext = new AopApplicationContext("application.json");
        aopApplictionContext.init();

        TestService testService = (TestService) aopApplictionContext.getBean("testServiceProxy");

        testService.testMethod();


    }
}
