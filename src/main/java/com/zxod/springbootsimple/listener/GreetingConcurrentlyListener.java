package com.zxod.springbootsimple.listener;

import com.zxod.springbootsimple.module.Hello;
import com.zxod.springbootsimple.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

// MessageListenerOrderly：顺序消费。MessageListenerConcurrently：并发消费
@Slf4j
public class GreetingConcurrentlyListener implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
        Hello helloService = SpringContextUtils.getBean("hello");
        for(MessageExt messageExt:list){
            try {
                String messageBody = new String(messageExt.getBody(), "utf-8");
                log.info("接收到消息：【{}】", messageBody);
                Thread.sleep(2000);
                helloService.sayHi(messageBody);
            } catch (Exception e) {
                log.error("fail to parse rmq message {}",messageExt.getBody(), e);
            }

        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
