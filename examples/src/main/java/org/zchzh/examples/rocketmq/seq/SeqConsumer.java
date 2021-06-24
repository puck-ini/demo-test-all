package org.zchzh.examples.rocketmq.seq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author zengchzh
 * @date 2021/6/24
 * 顺序消息消费,带事务方式（应用可控制Offset什么时候提交）
 */
public class SeqConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test_seq");
        consumer.setNamesrvAddr("localhost:9876");
        /*
          设置 consumer 第一次启动是从队列头部开始消费还是队列尾部开始消费
          如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicSeq", "TagA || TagC || TagD");
        consumer.registerMessageListener(new MessageListenerOrderly() {

            Random random = new Random();
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                for (MessageExt msg : list) {
                    // 可以看到每个 queue 有唯一的 consume 线程消费，订单对每个 queue（分区）有序
                    System.out.println("consumeThread=" + Thread.currentThread().getName()
                            + "queueId=" + msg.getQueueId()
                            + ", content:" + new String(msg.getBody()));
                }
                try {
                    //模拟业务逻辑处理中...
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer Started.");
    }
}
