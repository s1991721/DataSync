package com.jef.datasync.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by mr.lin on 2020/4/24
 * 简单分发模式，接收者
 */
@Component
@Slf4j
public class SimpleConsumer {

    @RabbitListener(queuesToDeclare = @Queue(DirectExchangeConfig.DIRECT_QUEUE_NAME))//消费者声明队列
    public void receiveMsgFromDirect(String msg) {
        log.info("收到RabbitMQ来自{}的消息：{}", DirectExchangeConfig.DIRECT_QUEUE_NAME, msg);
    }

}