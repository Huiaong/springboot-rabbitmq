package com.huiaong.rabbitmq.producer.collection;

import com.huiaong.rabbitmq.producer.senders.HappySender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/rabbit-mq/producer")
public class RabbitMQCollection {

    private final HappySender happySender;


    @Autowired
    public RabbitMQCollection(HappySender happySender) {
        this.happySender = happySender;
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public void sendHappy(@RequestParam Map<String ,String> params){
        happySender.send(params);
    }
}
