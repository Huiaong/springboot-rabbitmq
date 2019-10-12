package com.huiaong.normal.admin.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huiaong.normal.trade.log.service.PurchaseOrderLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin/purchase-order")
public class purchaseOrders {

    @Reference
    private PurchaseOrderLogService purchaseOrderLogService;

    @RequestMapping(method = RequestMethod.PUT)
    public Boolean create() {
//        ...正常的订单创建业务
        return purchaseOrderLogService.create();
    }
}
