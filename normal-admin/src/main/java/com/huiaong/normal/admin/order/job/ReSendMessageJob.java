package com.huiaong.normal.admin.order.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huiaong.normal.trade.mq.dto.BrokerMessageLogDto;
import com.huiaong.normal.trade.mq.model.BrokerMessageLog;
import com.huiaong.normal.trade.mq.producer.BrokerMessageProducer;
import com.huiaong.normal.trade.mq.service.BrokerMessageLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ReSendMessageJob {

    @Reference
    private BrokerMessageLogService brokerMessageLogService;
    @Reference
    private BrokerMessageProducer brokerMessageProducer;

    @Scheduled(fixedRate = 1000 * 5, initialDelay = 1000 * 5)
    public void autoRetry(){

        List<BrokerMessageLogDto> brokerMessageLogDtoList = brokerMessageLogService.findArticleOneHundredFailToSendMessage();

        brokerMessageLogDtoList.forEach(brokerMessageLogDto -> {
            brokerMessageProducer.send(brokerMessageLogDto);
        });
    }

}
