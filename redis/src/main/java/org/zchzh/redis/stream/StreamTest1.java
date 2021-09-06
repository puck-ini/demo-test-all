package org.zchzh.redis.stream;

import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.zchzh.redis.TestClient;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zengchzh
 * @date 2021/9/6
 */
public class StreamTest1 {

    public static void main(String[] args) {
        RedissonClient client = TestClient.getSingleClient();
        RStream<String, Object> testStream = client.getStream("test-s-1");
        Map<StreamMessageId, Map<String, Object>> map
                = testStream.read(3, 100L, TimeUnit.SECONDS, new StreamMessageId(0));
        System.out.println(map);
        List<StreamMessageId> idList = map.keySet().stream().map(item -> new StreamMessageId(item.getId0(), item.getId1())).collect(Collectors.toList());
        StreamMessageId lastId = idList.get(idList.size() - 1);
        for (int i = 0; i < 10; i++) {
            Map<StreamMessageId, Map<String, Object>> map1
                    = testStream.read(3, 100L, TimeUnit.SECONDS, lastId);
            System.out.println(map1);
            List<StreamMessageId> test = map1.keySet().stream().map(item -> new StreamMessageId(item.getId0(), item.getId1())).collect(Collectors.toList());
            lastId =  test.get(test.size() - 1);
            System.out.println(i + " : ");
        }
//        Map<StreamMessageId, Map<String, Object>> map1
//                = testStream.read(3, 100L, TimeUnit.SECONDS, new StreamMessageId(0));
//        System.out.println(map1);
        client.shutdown();
    }
}
