package com.huiaong.normal.trade.impl.dao;


import com.huiaong.normal.trade.mq.model.BrokerMessageLog;
import org.springframework.stereotype.Repository;

@Repository
public interface BrokerMessageLogDao {


    BrokerMessageLog findById(Long id);

    BrokerMessageLog findByMessageId(String messageId);

    int create(BrokerMessageLog brokerMessageLog);

    int update(BrokerMessageLog brokerMessageLog);

}
