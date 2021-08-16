package org.zchzh.bloomfilter;

import io.rebloom.client.Client;


/**
 * @author zengchzh
 * @date 2020/10/26
 * 使用RedisBloom测试布隆过滤器，使用前需要在redis中添加RedisBloom模块
 */
public class RedisBloomFilterTest {

    public static void main(String[] args) {
        Client client = new Client("192.168.44.128", 6379);

        client.add("java-test","test1");
        System.out.println(client.exists("java-test","test1"));
        System.out.println(client.exists("java-test","test2"));
    }
}
