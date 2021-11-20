package com.jef.datasync.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by mr.lin on 2020/4/24
 * 简单分发模式，发送者
 */
@Component
public class SimpleProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendDirectMsg(String msg) {
        rabbitTemplate.convertAndSend(DirectExchangeConfig.DIRECT_EXCHANGE_NAME, DirectExchangeConfig.DIRECT_ROUTING_KEY, msg);
    }

}