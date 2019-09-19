package com.huiaong.normal.trade.sms.service;

import com.huiaong.normal.trade.mq.dto.BrokerMessageLogDto;

public interface NormalMessageService {
    Long create(BrokerMessageLogDto brokerMessageLogDto);

}
