package com.huiaong.normal.trade.impl.log.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.huiaong.normal.trade.log.service.PurchaseOrderLogService;
import com.huiaong.normal.trade.mq.provider.TradeMQProvider;
import com.huiaong.normal.trade.mq.service.TradeMQResponseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@org.springframework.stereotype.Service
@AllArgsConstructor
public class PurchaseOrderLogServiceImpl implements PurchaseOrderLogService {

    private final TradeMQProvider tradeMQProvider;

    @Override
    public Boolean create() {

        tradeMQProvider.send("purchase-order-log.create.exchange", "purchase-order-log.create.routing-key", "{\"purchaseOrderId\":\"1\",\"type\":\"1\"}");

        return Boolean.TRUE;
    }
}
