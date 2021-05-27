package org.zchzh.commonutil.discovery;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @author zengchzh
 * @date 2021/5/27
 */
public class ZkServiceImpl implements DiscoveryService {

    private CuratorFramework client;

    private static final String DEFAULT_PATH = "/zkservice";

    public ZkServiceImpl(String address) {
        client = CuratorFrameworkFactory.newClient(address,
                new RetryNTimes(10, 5000));
        client.start();
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(DEFAULT_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DiscoveryDTO> getInstances() {
        return null;
    }

    @Override
    public List<DiscoveryDTO> getInstances(String serviceName) {
        return null;
    }

    @Override
    public void register(DiscoveryDTO dto) {

    }

    @Override
    public void update(DiscoveryDTO dto) {

    }

    @Override
    public void update(List<DiscoveryDTO> dtoList) {

    }

    @Override
    public void remove(DiscoveryDTO dto) {

    }

    @Override
    public void remove(List<DiscoveryDTO> dtoList) {

    }
}
