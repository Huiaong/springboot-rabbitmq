package com.huiaong.normal.trade.impl.mq.dao;

import com.huiaong.normal.trade.mq.model.TradeMQResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeMQResponseDao {
    int create(TradeMQResponse tradeMQResponse);

    int update(TradeMQResponse tradeMQResponse);

    TradeMQResponse findByMessageId(String messageId);

    List<TradeMQResponse> findArticleOneHundredFailToSendMessage();
}
