package org.zchzh.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author zengchzh
 * @date 2021/7/27
 */
public class ZkLock implements Lock {

    private static final String LOCK_NODE = "/zklock";

    private static final String SEPARATOR = "/";

    private static final String LOCK_NAME = "lock";

    private ZkClient zkClient;

    private String preNode;

    private String currentNode;

    CountDownLatch countDownLatch = null;

    public ZkLock(String address) {
        zkClient = new ZkClient(address);
        zkClient.setZkSerializer(new SerializableSerializer());
        synchronized (ZkLock.class) {
            if (!zkClient.exists(LOCK_NODE)) {
                zkClient.createPersistent(LOCK_NODE);
            }
        }
    }

    @Override
    public void lock() {
        if (tryLock()) {
            return;
        } {
            waitUnlock();
            lock();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        String rootNode = LOCK_NODE + SEPARATOR;
        if (Objects.isNull(currentNode) || "".equals(currentNode)) {
            currentNode = zkClient.createEphemeralSequential(rootNode, LOCK_NAME);
        }
        List<String> children = zkClient.getChildren(LOCK_NODE);
        Collections.sort(children);
        String minNode = children.get(0);
        boolean isEquals = currentNode.equals(rootNode + minNode);
        if (!isEquals) {
            int index = Collections.binarySearch(children, currentNode.substring(rootNode.length()));
            String lastNodeChild = children.get(index - 1);
            preNode = rootNode + lastNodeChild;
        }
        return isEquals;
    }

    private void waitUnlock() {
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                countDownLatch.countDown();;
            }
        };
        zkClient.subscribeDataChanges(preNode, listener);
        if (zkClient.exists(preNode)) {
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        zkClient.subscribeDataChanges(preNode, listener);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unlock() {
        if (Objects.nonNull(zkClient)) {
            zkClient.delete(currentNode);
            zkClient.close();;
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
