package com.huiaong.normal.trade.impl.mq.config;

import com.huiaong.normal.trade.impl.mq.callback.TradeConfirmCallback;
import com.huiaong.normal.trade.mq.service.TradeMQResponseService;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.huiaong.normal.trade.impl.mq")
public class TradeMQConfiguration {

    @Bean
    public RabbitTemplate rabbitTemplate(
            CachingConnectionFactory factory, TradeMQResponseService tradeMQResponseService) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setConfirmCallback(new TradeConfirmCallback(tradeMQResponseService));
        return rabbitTemplate;
    }
}
