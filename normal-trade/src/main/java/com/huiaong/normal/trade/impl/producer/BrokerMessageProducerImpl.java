package com.huiaong.normal.trade.impl.producer;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Strings;
import com.huiaong.normal.trade.impl.dao.BrokerMessageLogDao;
import com.huiaong.normal.trade.mq.dto.BrokerMessageLogDto;
import com.huiaong.normal.trade.mq.enums.BrokerMessageStatus;
import com.huiaong.normal.trade.mq.model.BrokerMessageLog;
import com.huiaong.normal.trade.mq.producer.BrokerMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
@Slf4j
public class BrokerMessageProducerImpl implements BrokerMessageProducer, RabbitTemplate.ConfirmCallback {

    private final RabbitTemplate rabbitTemplate;
    private final BrokerMessageLogDao brokerMessageLogDao;

    @Autowired
    public BrokerMessageProducerImpl(BrokerMessageLogDao brokerMessageLogDao, RabbitTemplate rabbitTemplate) {
        this.brokerMessageLogDao = brokerMessageLogDao;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(BrokerMessageLogDto brokerMessageLogDto) {
//        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(brokerMessageLogDto.getMessageId().toString());
        rabbitTemplate.convertAndSend(brokerMessageLogDto.getExchangeName(), brokerMessageLogDto.getRoutingKey(), brokerMessageLogDto.getMessage(), correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String messageId = correlationData.getId();
        if (Strings.isNullOrEmpty(messageId)) {
            log.error("message id not allow null");
            throw new RuntimeException("message id not allow null");
        }

        BrokerMessageLog brokerMessageLog = brokerMessageLogDao.findByMessageId(messageId);

        if (ack) {
            brokerMessageLog.setStatus(BrokerMessageStatus.HAS_SEND.value());
            brokerMessageLogDao.update(brokerMessageLog);
            log.info("message(id:{}) send success", messageId);
        } else {
            log.error("message(id:{}) send error, cause by:{}", messageId, cause);
        }
    }

//    @Override
//    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//        Long relationId = Long.valueOf(message.getMessageProperties().getHeaders().get("spring_returned_message_correlation").toString());
//        BrokerMessageLog brokerMessageLog = brokerMessageLogDao.findById(relationId);
//        brokerMessageLog.setStatus(BrokerMessageStatus.FAIL_SEND.value());
//        int updateResult = brokerMessageLogDao.update(brokerMessageLog);
//        log.info("update:{}", updateResult);
//        log.error("message(id:{}) routing(key:{}) to queue error, exchange:{}, cause by:{}:{}", relationId, routingKey, exchange, replyCode, replyText);
//    }
}
