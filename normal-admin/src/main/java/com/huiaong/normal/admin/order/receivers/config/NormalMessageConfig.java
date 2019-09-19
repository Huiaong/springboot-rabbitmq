package com.huiaong.normal.admin.order.receivers.config;

import com.google.common.collect.Maps;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class NormalMessageConfig {

    @Bean
    public FanoutExchange normalMessageDelayExchange(){
        return (FanoutExchange) ExchangeBuilder.fanoutExchange("normal-message.delay-alternate.exchange").durable(true).build();
    }

    @Bean
    public Queue normalMessageDelayQueue(){
        Map<String ,Object> arguments = Maps.newHashMap();
        arguments.put("x-message-ttl", 1000 * 5);
        arguments.put("x-dead-letter-exchange", "normal-message.send.alternate-exchange");
        return QueueBuilder.durable("normal-message.delay-alternate.queue").withArguments(arguments).build();
    }

    @Bean
    public Binding businessBinding(@Qualifier("normalMessageDelayQueue") Queue queue,
                                   @Qualifier("normalMessageDelayExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }
}
