package com.huiaong.normal.trade.impl.order.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.huiaong.normal.trade.impl.order.manager.PurchaseOrderManager;
import com.huiaong.normal.trade.order.service.PurchaseOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@org.springframework.stereotype.Service
@AllArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

//    private final PurchaseOrderDao purchaseOrderDao;
    private final PurchaseOrderManager purchaseOrderManager;

    @Override
    public Boolean create(
//            PurchaseOrder purchaseOrder
    ) {
        return purchaseOrderManager.doCreate(
//                purchaseOrder
        );
    }
}
