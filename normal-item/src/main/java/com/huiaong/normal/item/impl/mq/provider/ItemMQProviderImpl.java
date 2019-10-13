package com.huiaong.normal.item.impl.mq.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.huiaong.normal.common.util.DateUtils;
import com.huiaong.normal.common.util.JsonMapper;
import com.huiaong.normal.item.mq.provider.ItemMQProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@org.springframework.stereotype.Service
@AllArgsConstructor
public class ItemMQProviderImpl implements ItemMQProvider {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(String jsonEvent) {
        Map map = JsonMapper.nonDefaultMapper().fromJson(jsonEvent, Map.class);
        String exchange = (String) map.get("exchange");
        String routingKey = (String) map.get("routingKey");
        String event = (String) map.get("event");
        String messageId = (String) map.get("messageId");
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(messageId);
        this.send(exchange, routingKey, event, correlationData);
    }

    @Override
    public void send(String exchange, String routingKey, String event) {

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString().replace("-", "") + DateUtils.formatDate(new Date(), "yyyyMMdd"));
        this.send(exchange, routingKey, event, correlationData);
    }

    @Override
    public void send(String exchange, String routingKey, String event, CorrelationData correlationData) {
        log.info("id:----------------------------------{}", correlationData.getId());
        rabbitTemplate.convertAndSend(exchange, routingKey, event, correlationData);

        Map<String, String> mqProperties = Maps.newHashMap();
        mqProperties.put("exchange", exchange);
        mqProperties.put("routingKey", routingKey);
        mqProperties.put("event", event);
        mqProperties.put("messageId", correlationData.getId());
        event = JsonMapper.JSON_NON_EMPTY_MAPPER.toJson(mqProperties);

        rabbitTemplate.convertAndSend("mq-reliable-delivery.delay.exchange", "mq-reliable-delivery.delay.routing-key", event, correlationData);
    }
}
