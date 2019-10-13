package com.huiaong.normal.item.mq.service;

import com.huiaong.normal.item.mq.model.NormalReliableDelivery;

public interface ReliableDeliveryService {

    Boolean create(NormalReliableDelivery normalReliableDelivery);

    NormalReliableDelivery findByMessageId(String messageId);
}
