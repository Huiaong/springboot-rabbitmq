package com.huiaong.normal.trade.impl.mq.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.huiaong.normal.common.util.DateUtils;
import com.huiaong.normal.trade.mq.enums.TradeMQResponseStatus;
import com.huiaong.normal.trade.mq.model.TradeMQResponse;
import com.huiaong.normal.trade.mq.provider.TradeMQProvider;
import com.huiaong.normal.trade.mq.service.TradeMQResponseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@org.springframework.stereotype.Service
@AllArgsConstructor
public class TradeMQProviderImpl implements TradeMQProvider {

    private final RabbitTemplate rabbitTemplate;
    private final TradeMQResponseService tradeMQResponseService;

    @Override
    public void send(TradeMQResponse tradeMQResponse) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(tradeMQResponse.getMessageId());

        tradeMQResponse.setRetryCount(tradeMQResponse.getRetryCount() + 1);
        Date date = generateNextRetry(tradeMQResponse.getRetryCount());
        tradeMQResponse.setNextRetry(date);

        Boolean update = tradeMQResponseService.update(tradeMQResponse);
        if (update)
            this.send(tradeMQResponse.getExchange(), tradeMQResponse.getRoutingKey(), tradeMQResponse.getContent(), correlationData);
    }

    @Override
    public void send(String exchange, String routingKey, String event) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString().replace("-", "") + DateUtils.formatDate(new Date(), "yyyyMMdd"));

        TradeMQResponse tradeMQResponse = new TradeMQResponse();
        tradeMQResponse.setMessageId(correlationData.getId());
        tradeMQResponse.setContent(event);
        tradeMQResponse.setStatus(TradeMQResponseStatus.WAIT_SEND.value());
        tradeMQResponse.setExchange(exchange);
        tradeMQResponse.setRoutingKey(routingKey);
        tradeMQResponse.setRetryCount(0);
        tradeMQResponse.setNextRetry(DateUtils.withTheTimeAfter(new Date(), (long) 1000 * 60));

        Boolean aBoolean = tradeMQResponseService.create(tradeMQResponse);

        if (aBoolean) this.send(exchange, routingKey, event, correlationData);
    }

    @Override
    public void send(String exchange, String routingKey, String event, CorrelationData correlationData) {
        log.info("start sent mq:({}) to:({}) with:({}), correlationData:({})", event, exchange, routingKey, correlationData);
        rabbitTemplate.convertAndSend(exchange, routingKey, event, correlationData);
    }

    /**
     * 构建 下次重试时间
     *
     * @param retryCount 重试次数
     * @return 下次重试时间
     */
    private Date generateNextRetry(Integer retryCount) {
        Date date = new Date();
        switch (retryCount) {
            case 1:
                date = DateUtils.withTheTimeAfter(date, 1000 * 60 * 5L);
                break;
            case 2:
                date = DateUtils.withTheTimeAfter(date, 1000 * 60 * 10L);
                break;
            case 3:
                date = DateUtils.withTheTimeAfter(date, 1000 * 60 * 15L);
                break;
            default:
                return null;
        }
        return date;
    }
}
