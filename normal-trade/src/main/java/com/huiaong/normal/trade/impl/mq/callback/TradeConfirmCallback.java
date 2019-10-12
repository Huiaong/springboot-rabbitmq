package com.huiaong.normal.trade.impl.mq.callback;

import com.huiaong.normal.trade.mq.enums.TradeMQResponseStatus;
import com.huiaong.normal.trade.mq.model.TradeMQResponse;
import com.huiaong.normal.trade.mq.service.TradeMQResponseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class TradeConfirmCallback implements RabbitTemplate.ConfirmCallback {
    private final Integer MAX_RETRY_COUNT = 3;

    private final TradeMQResponseService tradeMQResponseService;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        String messageId = correlationData.getId();

        TradeMQResponse tradeMQResponse = tradeMQResponseService.findByMessageId(messageId);

        if (ack) {
            tradeMQResponse.setStatus(TradeMQResponseStatus.HAS_SEND.value());
            tradeMQResponseService.update(tradeMQResponse);
            log.info("message(id:{}) send success", messageId);
        } else if (Objects.equals(tradeMQResponse.getRetryCount(), MAX_RETRY_COUNT)){
            tradeMQResponse.setStatus(TradeMQResponseStatus.FAIL_SEND.value());
            tradeMQResponseService.update(tradeMQResponse);
            log.info("message(id:{}) send fail, retry count get to max", messageId);
        }else {
            log.error("message(id:{}) send error, cause by:{}", messageId, cause);
        }

    }
}
