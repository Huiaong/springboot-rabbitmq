package com.huiaong.normal.trade.impl;

import com.huiaong.normal.trade.mq.model.BrokerMessageLog;
import com.huiaong.normal.trade.mq.service.BrokerMessageLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class NormalTradeApplicationTests {

    @Autowired
    private BrokerMessageLogService brokerMessageLogService;

    @Test
    public void contextLoads() {
        BrokerMessageLog brokerMessageLog = brokerMessageLogService.findById(1L);
        log.info("brokerMessageLog:{}", brokerMessageLog);
    }

}
