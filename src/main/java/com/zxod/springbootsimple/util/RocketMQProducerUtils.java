package com.zxod.springbootsimple.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RocketMQProducerUtils implements InitializingBean {
    private static DefaultMQProducer producer = null;

    @Value("${rocketmq.host}:${rocketmq.port}")
    private String namesrvAddr;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            producer = new DefaultMQProducer("test-producer");
            producer.setNamesrvAddr(namesrvAddr);
            producer.setDefaultTopicQueueNums(0); // 默认投递到哪个queue里
            producer.start();
            log.info("RocketMQ start succeed");
        } catch (Exception e) {
            log.error("RocketMQ start failed！", e);
        }
    }

    public static SendStatus send(String topic, String tags, String body) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message msg = new Message(topic, tags, body.getBytes());
        return producer.send(msg).getSendStatus();
    }
    
}
