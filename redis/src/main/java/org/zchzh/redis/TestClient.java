package org.zchzh.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author zengchzh
 * @date 2021/9/2
 */
public class TestClient {

    public static RedissonClient getSingleClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("127.0.0.1:6379");
        return Redisson.create(config);
    }


    public static RedissonClient getClusterClient() {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("127.0.0.1:6379", "127.0.0.1:6379");
        return Redisson.create(config);
    }
}
