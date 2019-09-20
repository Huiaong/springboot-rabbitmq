package com.huiaong.normal.trade.mq.service;


import com.huiaong.normal.trade.mq.dto.BrokerMessageLogDto;
import com.huiaong.normal.trade.mq.model.BrokerMessageLog;

import java.util.List;

public interface BrokerMessageLogService {

    BrokerMessageLog findById(Long id);

    BrokerMessageLog findByMessageId(String messageId);

    BrokerMessageLog create(BrokerMessageLog brokerMessageLog);

    BrokerMessageLog update(BrokerMessageLog brokerMessageLog);

    List<BrokerMessageLogDto> findArticleOneHundredFailToSendMessage();

    BrokerMessageLog updateByMessageId(BrokerMessageLog brokerMessageLog);
}
