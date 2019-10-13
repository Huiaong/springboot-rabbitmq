package com.huiaong.normal.item.impl.mq.dao;

import com.huiaong.normal.item.mq.model.NormalReliableDelivery;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliableDeliveryDao {
    Boolean create(NormalReliableDelivery normalReliableDelivery);

    NormalReliableDelivery findByMessageId(String messageId);
}
