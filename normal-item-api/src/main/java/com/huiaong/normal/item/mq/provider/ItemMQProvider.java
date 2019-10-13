package com.huiaong.normal.item.mq.provider;

import org.springframework.amqp.rabbit.connection.CorrelationData;

public interface ItemMQProvider {
    void send(String event);

    void send(String exchange, String routingKey, String event);

    void send(String exchange, String routingKey, String event, CorrelationData correlationData);

}
