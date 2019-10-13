package com.huiaong.normal.crontab.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huiaong.normal.trade.mq.model.TradeMQResponse;
import com.huiaong.normal.trade.mq.provider.TradeMQProvider;
import com.huiaong.normal.trade.mq.service.TradeMQResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class ReSendOrderMessageJob {

    @Reference
    private TradeMQResponseService tradeMQResponseService;
    @Reference
    private TradeMQProvider tradeMQProvider;


    @Scheduled(fixedRate = 1000 * 5, initialDelay = 1000 * 5)
    public void autoRetry() {
        List<TradeMQResponse> tradeMQResponseList = tradeMQResponseService.findArticleOneHundredFailToSendMessage();
        if (CollectionUtils.isEmpty(tradeMQResponseList)) return;
        tradeMQResponseList.forEach(tradeMQProvider::send);
    }

}
