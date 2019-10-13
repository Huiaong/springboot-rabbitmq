package com.huiaong.normal.item.impl.log.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.huiaong.normal.item.log.service.CartLogService;
import com.huiaong.normal.item.mq.provider.ItemMQProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@org.springframework.stereotype.Service
@AllArgsConstructor
public class CartLogServiceImpl implements CartLogService {

    private final ItemMQProvider itemMQProvider;


    @Override
    public Boolean create() {

        itemMQProvider.send("cart-log.create.exchange", "cart-log.create.routing-key", "{\"userId\":\"1\",\"itemId\":\"1\"}");

        return Boolean.TRUE;
    }
}
