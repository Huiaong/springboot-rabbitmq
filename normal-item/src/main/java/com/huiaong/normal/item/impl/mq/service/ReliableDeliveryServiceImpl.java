package com.huiaong.normal.item.impl.mq.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.huiaong.normal.item.impl.mq.dao.ReliableDeliveryDao;
import com.huiaong.normal.item.mq.model.NormalReliableDelivery;
import com.huiaong.normal.item.mq.service.ReliableDeliveryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@org.springframework.stereotype.Service
@AllArgsConstructor
public class ReliableDeliveryServiceImpl implements ReliableDeliveryService {
    private final ReliableDeliveryDao reliableDeliveryDao;

    @Override
    public Boolean create(NormalReliableDelivery normalReliableDelivery) {
        return reliableDeliveryDao.create(normalReliableDelivery);
    }

    @Override
    public NormalReliableDelivery findByMessageId(String messageId) {
        return reliableDeliveryDao.findByMessageId(messageId);
    }
}
