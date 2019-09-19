package com.huiaong.rabbitmq.consumer.receivers;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class NormalMessageReceiver {


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "normal-message.send.queue",
                    durable = "true"
            ),
            exchange = @Exchange(
                    value = "normal-message.send.exchange",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"
            ),
            key = "normal-message.send.routing-key"
    ))
    public void process1(Object hello, Channel channel, Message message) throws IOException {

        log.info("-------------------------消息收到了：{}------------------------", hello.toString());
        int random = 1 + (int) (Math.random() * 10);
        if (random % 2 == 0) {

//             手动确认消息，
//             当multiple为false，只确认当前的消息。
//             当multiple为true，批量确认所有比当前deliveryTag小的消息。
//             deliveryTag是用来标识Channel中投递的消息。
//             RabbitMQ保证在每个Channel中，消息的deliveryTag是从1递增。
            log.info("-------------------------消息处理成功------------------------");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
//            当消费端处理消息异常时，我们可以选择处理失败消息的方式。
//            如果requeue为true，失败消息会重新进入Queue，
//            试想一下，如果消费者在消费时发生异常，那么就不会对这一次消息进行ACK，
//            进而发生回滚消息的操作，使消息始终放在Queue的头部，然后不断的被处理和回滚，
//            导致队列陷入死循环，为了解决这种问题，我们可以引入重试机制(当重试次数超过最大值，丢弃该消息)或者是死信队列+重试队列。
//            requeue为false，丢弃该消息。
            log.info("-------------------------消息处理失败------------------------");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
