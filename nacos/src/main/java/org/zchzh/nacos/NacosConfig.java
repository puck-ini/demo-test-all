package org.zchzh.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author zengchzh
 * @date 2021/8/23
 */
public class NacosConfig {

    private ConfigService configService;

    private static final String DEFAULT_GROUP = "DEFAULT_GROUP";

    private Listener listener = new Listener() {
        @Override
        public Executor getExecutor() {
            return null;
        }

        @Override
        public void receiveConfigInfo(String s) {
            System.out.println("new config = " + s);
        }
    };

    public NacosConfig() {
        try {
            configService = NacosFactory.createConfigService("127.0.0.1:8848");
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }


    public void publishConfig(String dataId, String content) {
        try {
            boolean isSuccess = configService.publishConfig(dataId, DEFAULT_GROUP, content);
            if (isSuccess) {
                System.out.println("dataId = " + dataId + ", group = " + DEFAULT_GROUP + ", content = " + content);
            } else {
                System.out.println("publish config error");
            }
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public void getConfig(String dataId) {
        try {
            String content = configService.getConfig(dataId, DEFAULT_GROUP, 3000);
            System.out.println(content);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public void configListener(String dataId) {
        try {
            configService.addListener(dataId, DEFAULT_GROUP, listener);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public void removeListener(String dataId) {
        System.out.println("remove listener");
        configService.removeListener(dataId, DEFAULT_GROUP, listener);
    }


    public void removeConfig(String dataId) {
        System.out.println("remove config");
        try {
            configService.removeConfig(dataId, DEFAULT_GROUP);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String dataId = "testdata.txt";
        String content = "test: \n" +
                "  msg: 123\n" +
                "  address: qewq&*\n";
        NacosConfig config = new NacosConfig();
        config.publishConfig(dataId, content);
        TimeUnit.MILLISECONDS.sleep(50);
        config.getConfig(dataId);
        config.configListener(dataId);
        TimeUnit.SECONDS.sleep(5);
        String content1 = "test: \n" +
                "  msg: 123\n" +
                "  address: 098\n";
        config.publishConfig(dataId, content1);
        TimeUnit.MILLISECONDS.sleep(50);
        config.getConfig(dataId);
        TimeUnit.SECONDS.sleep(10);
        config.removeListener(dataId);
        config.publishConfig(dataId, content1);
        TimeUnit.MILLISECONDS.sleep(50);
        config.getConfig(dataId);
        config.removeConfig(dataId);
        TimeUnit.SECONDS.sleep(10);
    }
}
