package org.zchzh.commonutil;


import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author zengchzh
 * @date 2021/3/24
 */
public class CglibTest {


    public static void main(String[] args) {
        while (true) {
            List<String> uuidList = Collections.synchronizedList(new ArrayList<>());
//        List<String> uuidList = new ArrayList<>();
            long start = System.currentTimeMillis();
//            System.out.println(start);
            LongStream.range(0,10000).parallel().forEach(i->{
                uuidList.add(UUID.randomUUID().toString());
            });
            long end = System.currentTimeMillis();
            System.out.println(end - start);

//        uuidList.stream().parallel().map(String::getBytes).collect(Collectors.toList());
            List<String> uuidFilterList = uuidList.stream().distinct().collect(Collectors.toList());

//            System.out.println("生成id数："+uuidList.size());
//            System.out.println("过滤重复后id数："+uuidFilterList.size());
//            System.out.println("重复id数："+(uuidList.size()-uuidFilterList.size()));
        }


//
//
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(DemoTest.class);
//        enhancer.setCallback(new DemoMethodInte());
//
//        DemoTest demoTest = (DemoTest) enhancer.create();
//        demoTest.print("123");

    }



    public interface DemoTest {

        String print(String msg);
    }


    public static class DemoTestImpl implements DemoTest{

        @Override
        public String print(String msg) {
            System.out.println("msg = " + msg);
            return msg;
        }
    }

    public static class DemoMethodInte implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

            System.out.println("object class = " + o.getClass().getName());
            before();
            methodProxy.invokeSuper(o,objects);
//            method.invoke(o,objects);
            after();
            System.out.println("method = " + method);
            return null;
        }


        private void before(){
            System.out.println("before");
        }

        private void after() {
            System.out.println("after");
        }
    }
}
