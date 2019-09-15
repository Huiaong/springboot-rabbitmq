package com.huiaong.rabbitmq.producer.test.send;


import com.huiaong.rabbitmq.producer.senders.HappySender;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SenderTest {

    private final HappySender happySender;

    @Autowired
    public SenderTest(HappySender happySender) {
        this.happySender = happySender;
    }

    @Test
    public void happySenderTest(){
//        happySender.send(Maps.newHashMap());
    }
}
