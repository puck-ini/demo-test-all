package org.zchzh.rocketmq.oneway;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author zengchzh
 * @date 2021/6/23
 * 单向发送消息，不关心结果
 */
public class OneWayProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, UnsupportedEncodingException {
        DefaultMQProducer producer = new DefaultMQProducer("test_one_way");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicOneWay" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送单向消息，没有任何返回结果
            producer.sendOneway(msg);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();

    }
}
