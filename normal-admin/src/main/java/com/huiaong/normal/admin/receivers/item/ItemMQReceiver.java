package com.huiaong.normal.admin.receivers.item;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huiaong.normal.common.util.JsonMapper;
import com.huiaong.normal.item.mq.model.NormalReliableDelivery;
import com.huiaong.normal.item.mq.provider.ItemMQProvider;
import com.huiaong.normal.item.mq.service.ReliableDeliveryService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class ItemMQReceiver {

    @Reference
    private ReliableDeliveryService reliableDeliveryService;
    @Reference
    private ItemMQProvider itemMQProvider;

    @RabbitListener(queues = "cart-log.create.queue")
    public void process(
            @Payload String event,
            @Headers Map<String, Object> properties,
            Channel channel
    ) throws IOException {
        log.info("receiver message:{}", event);
        String messageId = (String) properties.get("spring_returned_message_correlation");


        Long deliveryTag = (Long) properties.get(AmqpHeaders.DELIVERY_TAG);

        int random = 1 + (int) (Math.random() * 10);
        if (random % 2 == 0) {
            NormalReliableDelivery reliableDelivery = new NormalReliableDelivery();
            reliableDelivery.setMessageId(messageId);

            Boolean aBoolean = reliableDeliveryService.create(reliableDelivery);

            log.info("-------------------------消息处理成功------------------------");
            channel.basicAck(deliveryTag, false);
        } else {
            log.info("-------------------------消息处理失败------------------------");
            channel.basicNack(deliveryTag, false, true);
        }
    }

    @RabbitListener(queues = "mq-reliable-delivery.queue")
    public void processReliableDelivery(
            @Payload String event,
            @Headers Map<String, Object> properties,
            Channel channel
    ) throws IOException {
        String messageId = (String) properties.get("spring_returned_message_correlation");
        Long deliveryTag = (Long) properties.get(AmqpHeaders.DELIVERY_TAG);


        NormalReliableDelivery reliableDelivery = reliableDeliveryService.findByMessageId(messageId);
        if (Objects.isNull(reliableDelivery)) {
            itemMQProvider.send(event);
        }
        channel.basicAck(deliveryTag, false);
    }
}
