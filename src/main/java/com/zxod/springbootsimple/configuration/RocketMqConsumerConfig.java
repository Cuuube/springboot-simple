package com.zxod.springbootsimple.configuration;

import com.zxod.springbootsimple.listener.GreetingConcurrentlyListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RocketMqConsumerConfig {

    @Value("${rocketmq.host}:${rocketmq.port}")
    private String namesrvAddr;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnExpression("${rocketmq.enable:false}")
    public DefaultMQPushConsumer testRocketMQPushConsumer() { // 一号consumer
        String consumerGroup = "test-consumer";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setMessageModel(MessageModel.CLUSTERING); // 集群模式
        // consumer.setMessageModel(MessageModel.BROADCASTING); // 广播模式

        try {
            // topic: greeting; tags: cn, us
            consumer.subscribe("greeting", "us");
            consumer.registerMessageListener(new GreetingConcurrentlyListener());
            // consumer.registerMessageListener(new GreetingOrderlyListener(consumerGroup));
            consumer.start();

            // // 增加优雅降级。废弃：配置@Bean(destroyMethod = "shutdown")代替
            // Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //     log.info("consumer{}即将优雅关闭", consumerGroup);
            //     consumer.shutdown();
            // }));

            log.info("testRocketMQPushConsumer start!");
            return consumer;
        } catch (Exception e) {
            log.error("testRocketMQPushConsumer start failed!", e);
            return null;
        }
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnExpression("${rocketmq.enable:false}")
    public DefaultMQPushConsumer testRocketMQPushConsumer2() { // 二号consumer
        String consumerGroup = "test-consumer2";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setMessageModel(MessageModel.CLUSTERING); // 集群模式
        // consumer.setMessageModel(MessageModel.BROADCASTING); // 广播模式

        try {
            // topic: greeting; tags: cn, us
            consumer.subscribe("greeting", "cn");
            consumer.registerMessageListener(new GreetingConcurrentlyListener());
            // consumer.registerMessageListener(new GreetingOrderlyListener(consumerGroup));
            consumer.start();

            log.info("testRocketMQPushConsumer start!");
            return consumer;
        } catch (Exception e) {
            log.error("testRocketMQPushConsumer start failed!", e);
            return null;
        }
    }
}
