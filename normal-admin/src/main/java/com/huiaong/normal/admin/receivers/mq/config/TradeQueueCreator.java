package com.huiaong.normal.admin.receivers.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TradeQueueCreator {
    @Bean
    public Queue purchaseOrderLogQueue() {
        return new Queue("purchase-order-log.create.queue", true, false, false);
    }

    @Bean
    public DirectExchange purchaseOrderLogExchange() {
        return new DirectExchange("purchase-order-log.create.exchange");
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(purchaseOrderLogQueue()).to(purchaseOrderLogExchange()).with("purchase-order-log.create.routing-key");
    }

}
