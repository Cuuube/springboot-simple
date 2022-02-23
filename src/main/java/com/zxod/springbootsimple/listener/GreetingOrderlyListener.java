package com.zxod.springbootsimple.listener;

import com.zxod.springbootsimple.module.Hello;
import com.zxod.springbootsimple.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

// MessageListenerOrderly：顺序消费。MessageListenerConcurrently：并发消费
@Slf4j
public class GreetingOrderlyListener implements MessageListenerOrderly {
    private String groupId;

    public GreetingOrderlyListener(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext context) {
        Hello helloService = SpringContextUtils.getBean("hello");
        for(MessageExt messageExt:list){
            try {
                String messageBody = new String(messageExt.getBody(), "utf-8");
                log.info("group {} 接收到消息：【{}】", groupId, messageBody);
                Thread.sleep(2000);
                helloService.sayHi(messageBody);
            } catch (Exception e) {
                log.error("fail to parse rmq message {}",messageExt.getBody(), e);
            }

        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
