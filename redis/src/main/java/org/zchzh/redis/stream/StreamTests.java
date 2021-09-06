package org.zchzh.redis.stream;

import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.zchzh.redis.TestClient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author zengchzh
 * @date 2021/9/3
 */
public class StreamTests {

    public static void main(String[] args) {
        RedissonClient client = TestClient.getSingleClient();
        RStream<String, Object> testStream = client.getStream("test-s-1");
        for (int i = 0; i < 10; i++) {
            testStream.add("1.1.1", new TestData("test1", 1, new BigDecimal("123.321"), new Date()));
        }
        client.shutdown();
    }

    static class TestData implements Serializable {
        private String msg;

        private Integer num;

        private BigDecimal price;

        private Date createTime;

        public TestData(String msg, Integer num, BigDecimal price, Date createTime) {
            this.msg = msg;
            this.num = num;
            this.price = price;
            this.createTime = createTime;
        }
    }
}
