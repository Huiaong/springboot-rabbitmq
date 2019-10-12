package com.huiaong.normal.trade.impl.mq.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.huiaong.normal.trade.impl.mq.dao.TradeMQResponseDao;
import com.huiaong.normal.trade.mq.model.TradeMQResponse;
import com.huiaong.normal.trade.mq.service.TradeMQResponseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@org.springframework.stereotype.Service
public class TradeMQResponseServiceImpl implements TradeMQResponseService {

    private final TradeMQResponseDao tradeMQResponseDao;

    @Override
    public Boolean create(TradeMQResponse tradeMQResponse) {
        return tradeMQResponseDao.create(tradeMQResponse) == 1;
    }

    @Override
    public Boolean update(TradeMQResponse tradeMQResponse) {
        return tradeMQResponseDao.update(tradeMQResponse) == 1;
    }

    @Override
    public TradeMQResponse findByMessageId(String messageId) {
        return tradeMQResponseDao.findByMessageId(messageId);
    }

    @Override
    public List<TradeMQResponse> findArticleOneHundredFailToSendMessage() {
        return tradeMQResponseDao.findArticleOneHundredFailToSendMessage();
    }
}
