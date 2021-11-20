package com.jef.datasync.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by mr.lin on 2020/4/23
 * Direct交换机配置
 * 配置项取代了之前通过exchangeDeclare、queueDeclare声明交换机和队列的操作
 */
@Configuration
public class DirectExchangeConfig {

    //EXCHANGE_NAME为空则使用系统默认的DirectExchange
    public static final String DIRECT_EXCHANGE_NAME = "direct_exchange";
    public static final String DIRECT_QUEUE_NAME = "direct_queue";
    public static final String DIRECT_ROUTING_KEY = "direct_routing_key";

    /**
     * @param name       交换机名称.
     * @param durable    持久化，MQ重启后仍存在
     * @param autoDelete 没使用者自动删除
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME, true, false);
    }

    /**
     * @param name       队列名称
     * @param durable    持久化，MQ重启后仍存在
     * @param exclusive  独占，只能声明者可连接
     * @param autoDelete 没使用者自动删除
     */
    @Bean
    public Queue queue() {
        return new Queue(DIRECT_QUEUE_NAME, true, false, false);
    }

    /**
     * 交换机与队列的绑定操作
     *
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(DIRECT_ROUTING_KEY);
    }

}