package com.huiaong.normal.trade.impl.order.manager;

import com.huiaong.normal.trade.log.service.PurchaseOrderLogService;
import com.huiaong.normal.trade.mq.provider.TradeMQProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@AllArgsConstructor
public class PurchaseOrderManager {

    private final PurchaseOrderLogService purchaseOrderLogService;


    @Transactional(rollbackFor = Exception.class)
    public Boolean doCreate() {


        return purchaseOrderLogService.create();
    }

}
