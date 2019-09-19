package com.huiaong.normal.admin.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huiaong.normal.trade.mq.dto.BrokerMessageLogDto;
import com.huiaong.normal.trade.mq.producer.BrokerMessageProducer;
import com.huiaong.normal.trade.mq.service.BrokerMessageLogService;
import com.huiaong.normal.trade.sms.enums.NormalMessagePlatform;
import com.huiaong.normal.trade.sms.service.NormalMessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/admin/purchase-order")
public class purchaseOrders {

    @Reference
    private BrokerMessageProducer brokerMessageProducer;
    @Reference
    private BrokerMessageLogService brokerMessageLogService;
    @Reference
    private NormalMessageService normalMessageService;


    @RequestMapping(value = "/order-cancel/{id}", method = RequestMethod.GET)
    public Boolean cancelOrder(@PathVariable Long id){
        /*
                  模拟消息推送业务
            取消 采购订单 消息推送给用户
        */
        BrokerMessageLogDto brokerMessageLogDto = buildNormalMessageDto();
        Long brokerMessageLogId = normalMessageService.create(brokerMessageLogDto);
        brokerMessageLogDto.setMessageId(brokerMessageLogId);

        brokerMessageProducer.send(brokerMessageLogDto);

        return Boolean.TRUE;
    }

    private BrokerMessageLogDto buildNormalMessageDto() {
        BrokerMessageLogDto brokerMessageLogDto = new BrokerMessageLogDto();
        brokerMessageLogDto.setMessage("purchase order has been cancel");
        brokerMessageLogDto.setExchangeName("normal-message.send.exchange");
        brokerMessageLogDto.setRoutingKey("normal-message.send.routing-key");
        brokerMessageLogDto.setPlatform(NormalMessagePlatform.IOS.value());
        brokerMessageLogDto.setSendTo(128L);
        return brokerMessageLogDto;
    }
}
