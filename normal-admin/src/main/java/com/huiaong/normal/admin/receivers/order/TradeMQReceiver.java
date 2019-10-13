package com.huiaong.normal.admin.receivers.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huiaong.normal.trade.mq.service.TradeMQResponseService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class TradeMQReceiver {

    @Reference
    private TradeMQResponseService tradeMQResponseService;

    @RabbitListener(queues = "purchase-order-log.create.queue")
    public void process(
            @Payload String event,
            @Headers Map<String, Object> properties,
            Channel channel
    ) throws IOException {

        log.info("receiver message:{}", event);
        Long deliveryTag = (Long) properties.get(AmqpHeaders.DELIVERY_TAG);

        int random = 1 + (int) (Math.random() * 10);
        if (random % 2 == 0) {

//             手动确认消息，
//             当multiple为false，只确认当前的消息。
//             当multiple为true，批量确认所有比当前deliveryTag小的消息。
//             deliveryTag是用来标识Channel中投递的消息。
//             RabbitMQ保证在每个Channel中，消息的deliveryTag是从1递增。
            log.info("-------------------------消息处理成功------------------------");
            channel.basicAck(deliveryTag, false);
        } else {
//            当消费端处理消息异常时，我们可以选择处理失败消息的方式。
//            如果requeue为true，失败消息会重新进入Queue，
//            试想一下，如果消费者在消费时发生异常，那么就不会对这一次消息进行ACK，
//            进而发生回滚消息的操作，使消息始终放在Queue的头部，然后不断的被处理和回滚，
//            导致队列陷入死循环，为了解决这种问题，我们可以引入重试机制(当重试次数超过最大值，丢弃该消息)或者是死信队列+重试队列。
//            requeue为false，丢弃该消息。
            log.info("-------------------------消息处理失败------------------------");
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
