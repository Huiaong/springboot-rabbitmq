package com.huiaong.rabbitmq.producer.senders;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huiaong.normal.trade.mq.model.BrokerMessageLog;
import com.huiaong.normal.trade.mq.service.BrokerMessageLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class HappySender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {


    private final RabbitTemplate rabbitTemplate;
    @Reference(version = "1.0.0")
    private BrokerMessageLogService brokerMessageLogService;

    @Autowired
    public HappySender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
    }

    public void send(Map<String, String> param) {

        this.rabbitTemplate.convertAndSend(param.get("exchange_name"), param.get("routing_key"), param.get("message"));
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("---------------------------hello confirm----------------------------");
        log.info("correlationData:{}, ack:{}, cause:{}", correlationData, ack, cause);
        if (ack) {
            log.info("消息投递到交换机成功 data:{}, cause by:{}", correlationData, cause);
        } else {
            log.error("消息投递到交换机失败 data:{}, cause by:{}", correlationData, cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("---------------------------hello return call back----------------------------");
        log.info("message:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}", message, replyCode, replyText, exchange, routingKey);
        log.info("消息从交换机投递到队列失败, message:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}", message, replyCode, replyText, exchange, routingKey);
    }
}
