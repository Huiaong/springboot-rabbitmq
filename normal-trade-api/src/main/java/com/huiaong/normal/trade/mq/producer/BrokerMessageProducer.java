package com.huiaong.normal.trade.mq.producer;

import com.huiaong.normal.trade.mq.dto.BrokerMessageLogDto;

public interface BrokerMessageProducer {

    void send(BrokerMessageLogDto brokerMessageLogDto);
}
