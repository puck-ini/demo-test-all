package org.zchzh.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zengchzh
 * @date 2021/8/23
 */
public class NacosNaming {

    private NamingService namingService;

    private EventListener listener = new EventListener() {
        @Override
        public void onEvent(Event event) {
            if (event instanceof NamingEvent) {
                System.out.println("event = " + event);
            }
        }
    };

    public NacosNaming() {
        try {
            namingService = NacosFactory.createNamingService("127.0.0.1:8848");
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Data
    static class ServiceInfo {
        private String serviceName;

        private String ip;

        private int port;

        public ServiceInfo(String serviceName, String ip, int port) {
            this.serviceName = serviceName;
            this.ip = ip;
            this.port = port;
        }
    }


    public void register(ServiceInfo info) {
        Instance instance = new Instance();
        instance.setIp(info.getIp());
        instance.setPort(info.getPort());
        instance.addMetadata("test", "123");
        instance.addMetadata("test1", "qwe&*");
        try {
            namingService.registerInstance(info.getServiceName(), instance);
            System.out.println("info = " + info + ", instance = " + instance);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public void get(String serviceName) {
        try {
            List<Instance> instanceList = namingService.getAllInstances(serviceName);
            for (Instance instance : instanceList) {
                System.out.println("instance = " + instance);
            }
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public void getHealthyInstance(String serviceName) {
        try {
            Instance instance = namingService.selectOneHealthyInstance(serviceName);
            System.out.println("healthy instance = " + instance);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public void namingListener(ServiceInfo info) {
        System.out.println("namingListener");
        try {
            namingService.subscribe(info.getServiceName(), listener);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public void removeListener(ServiceInfo info) {
        System.out.println("removeListener");
        try {
            namingService.unsubscribe(info.getServiceName(), listener);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public void remove(ServiceInfo info) {
        System.out.println("remove instance");
        try {
            namingService.deregisterInstance(info.getServiceName(), info.getIp(), info.getPort());
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NacosNaming naming = new NacosNaming();
        String serviceName = "test";
        ServiceInfo info = new ServiceInfo(serviceName, getIp(), 8099);
        naming.register(info);
        TimeUnit.MILLISECONDS.sleep(50);
        naming.get(serviceName);
        naming.namingListener(info);
        ServiceInfo info1 = new ServiceInfo(serviceName, getIp(), 8321);
        naming.register(info1);
        TimeUnit.MILLISECONDS.sleep(50);
        naming.get(serviceName);
        TimeUnit.SECONDS.sleep(5);
        naming.removeListener(info);
        ServiceInfo info2 = new ServiceInfo(serviceName, getIp(), 8333);
        naming.register(info2);
        TimeUnit.MILLISECONDS.sleep(50);
        naming.get(serviceName);
        TimeUnit.SECONDS.sleep(10);
        naming.remove(info);
        naming.remove(info1);
        naming.remove(info2);
    }


    private static String getIp() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
