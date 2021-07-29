package org.zchzh.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author zengchzh
 * @date 2021/7/27
 */
public class DisClient {

    public DisClient() {
        // 初始化zk的/distrilLock节点, 会出现线程安全问题
        // 使用同步锁，保证了检查和创建的安全性
        synchronized (DisClient.class)
        {
            if (!zkClient.exists("/distrilock"))
            {
                zkClient.createPersistent("/distrilock");
            }
        }

    }

    // 定义前一个节点
    String beforeNodePath;

    // 定义当前节点
    String currentNodePath;

    // 计数器, 用来使没有获得锁的线程进行等待
    CountDownLatch countDownLatch=null;

    // 获取到ZkClient, 连接zk集群
    private ZkClient zkClient = new ZkClient("linux121:2181, linux122:2181");

    // 把抢锁过程分为两部分，一部分是创建节点，比较序号。另一部分是等待锁

    // 完整获取锁方法
    public void getDisLock()
    {
        // 获取当前线程名称
        String threadName = Thread.currentThread().getName();
        // 首先调用tryGetLock
        if (tryGetLock())
        {
            // 说明获取到锁了
            System.out.println(threadName + ": 获取到了锁");
        }
        else{
            System.out.println(threadName + ": 获取锁失败, 进入等待状态");
        }
        // 没有获取到锁，则需要进行等待，同时监听其上一个节点
        waitForLock();
        // 递归获取锁。即等待结束后，可以调用getDisLock方法重新尝试获取锁
        getDisLock();
    }


    // 尝试获取锁
    public boolean tryGetLock()
    {
        // 在指定目录下创建临时顺序节点
        if (null == currentNodePath || "".equals(currentNodePath))
        {
            currentNodePath = zkClient.createEphemeralSequential("/distrilock/", "lock");
        }

        // 获取到路径下所有的子节点
        List<String> childs = zkClient.getChildren("/distrilock");
        // 对节点信息进行排序
        Collections.sort(childs); // 默认是升序
        String minNode = childs.get(0); // 获取序号最小的节点
        // 判断自己创建节点是否与最小序号一致
        if (currentNodePath.equals("/distrilock/" + minNode))
        {
            // 说明当前线程创建的就是序号最小节点
            return true;
        }
        else{
            // 说明最小节点不是自己创建的
            // 此时，要监控自己当前节点序号前一个的节点
            int i = Collections.binarySearch(childs, currentNodePath.substring("/distrilock/".length()));
            // 前一个(lastNodeChild是不包括父节点)
            String lastNodeChild = childs.get(i - 1);
            beforeNodePath = "/distrilock/" + lastNodeChild;
        }
        return false;
    }

    // 等待之前节点释放锁, 如何判断锁被释放，需要唤醒线程，继续尝试tryGetLock
    public void waitForLock() {
        // 准备一个监听器
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }
            @Override
            // 监听数据被删除
            public void handleDataDeleted(String s) throws Exception {
                // 提醒当前线程再次获取锁
                // 这里即当有前一个节点被删除时，唤醒下一个节点的线程
                countDownLatch.countDown(); // 把值减一，变为0，则唤醒之前await的线程
            }
        };

        // 监控前一个节点
        zkClient.subscribeDataChanges(beforeNodePath, iZkDataListener);

        // 在监听的通知没来之前，该线程应该是等待状态, 先判断一次上一个节点是否还存在
        if (zkClient.exists(beforeNodePath))
        {
            // 开始等待, CountDownLatch: 线程同步计数器
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await(); // 阻塞，直到countDownLatch值变为0
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // 此时监听结束，可以解除监听
        zkClient.subscribeDataChanges(beforeNodePath, iZkDataListener);

    }

    // 释放锁
    public void deleteLock()
    {
        if (zkClient!=null)
        {
            zkClient.delete(currentNodePath);
            zkClient.close();
            System.out.println(Thread.currentThread().getName() + ": 释放了锁资源");
        }
    }
}
