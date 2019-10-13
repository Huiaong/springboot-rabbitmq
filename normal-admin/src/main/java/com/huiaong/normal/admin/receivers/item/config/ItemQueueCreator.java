package com.huiaong.normal.admin.receivers.item.config;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ItemQueueCreator {

    @Setter
    @Value("${rabbitmq.reliableDelivery.delay}")
    private Integer reliableDeliveryDelay = 1000 * 60 * 5;

    //          cart log 正常消息
    @Bean
    public Queue cartLogQueue() {
        return new Queue("cart-log.create.queue", true, false, false);
    }

    @Bean
    public DirectExchange cartLogExchange() {
        return new DirectExchange("cart-log.create.exchange");
    }

    @Bean
    public Binding cartLogBinding() {
        return BindingBuilder.bind(cartLogQueue()).to(cartLogExchange()).with("cart-log.create.routing-key");
    }

    //          可靠性投递 延时队列  转发到可靠性投递检验队列
    @Bean
    public Queue reliableDeliveryDelayQueue() {
        Map<String, Object> arguments = Maps.newHashMap();
        arguments.put("x-message-ttl", reliableDeliveryDelay);
        arguments.put("x-dead-letter-exchange", "mq-reliable-delivery.exchange");
        arguments.put("x-dead-letter-routing-key", "mq-reliable-delivery.routing-key");
        return new Queue("mq-reliable-delivery.delay.queue", true, false, false, arguments);
    }

    @Bean
    public DirectExchange reliableDeliveryDelayExchange() {
        return new DirectExchange("mq-reliable-delivery.delay.exchange");
    }

    @Bean
    public Binding reliableDeliveryDelayBinding() {
        return BindingBuilder.bind(reliableDeliveryDelayQueue()).to(reliableDeliveryDelayExchange()).with("mq-reliable-delivery.delay.routing-key");
    }

    //          可靠性投递检验队列
    @Bean
    public Queue reliableDeliveryQueue() {
        return new Queue("mq-reliable-delivery.queue", true, false, false);
    }

    @Bean
    public DirectExchange reliableDeliveryExchange() {
        return new DirectExchange("mq-reliable-delivery.exchange");
    }

    @Bean
    public Binding reliableDeliveryBinding() {
        return BindingBuilder.bind(reliableDeliveryQueue()).to(reliableDeliveryExchange()).with("mq-reliable-delivery.routing-key");
    }
}
