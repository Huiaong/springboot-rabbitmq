package com.huiaong.normal.trade.mq.service;

import com.huiaong.normal.trade.mq.model.TradeMQResponse;

import java.util.List;

public interface TradeMQResponseService {

    Boolean create(TradeMQResponse tradeMQResponse);

    Boolean update(TradeMQResponse tradeMQResponse);

    TradeMQResponse findByMessageId(String messageId);

    List<TradeMQResponse> findArticleOneHundredFailToSendMessage();
}
