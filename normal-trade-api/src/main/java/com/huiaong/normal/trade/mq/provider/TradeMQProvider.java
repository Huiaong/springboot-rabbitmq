package com.huiaong.normal.trade.mq.provider;

import com.huiaong.normal.trade.mq.model.TradeMQResponse;
import org.springframework.amqp.rabbit.connection.CorrelationData;

public interface TradeMQProvider {

    void send(TradeMQResponse tradeMQResponse);

    void send(String exchange, String routingKey, String event);

    void send(String exchange, String routingKey, String event, CorrelationData correlationData);

}
