package com.huiaong.normal.trade.mq.service;


import com.huiaong.normal.trade.mq.model.BrokerMessageLog;

public interface BrokerMessageLogService {

    BrokerMessageLog findById(Long id);

    BrokerMessageLog findByMessageId(String messageId);

    BrokerMessageLog create(BrokerMessageLog brokerMessageLog);

    BrokerMessageLog update(BrokerMessageLog brokerMessageLog);
}
