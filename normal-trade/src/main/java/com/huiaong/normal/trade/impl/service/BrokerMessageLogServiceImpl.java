package com.huiaong.normal.trade.impl.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.huiaong.normal.trade.impl.dao.BrokerMessageLogDao;
import com.huiaong.normal.trade.mq.model.BrokerMessageLog;
import com.huiaong.normal.trade.mq.service.BrokerMessageLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service(version = "1.0.0")
@org.springframework.stereotype.Service
public class BrokerMessageLogServiceImpl implements BrokerMessageLogService {

    private final BrokerMessageLogDao brokerMessageLogDao;

    @Autowired
    public BrokerMessageLogServiceImpl(BrokerMessageLogDao brokerMessageLogDao) {
        this.brokerMessageLogDao = brokerMessageLogDao;
    }

    @Override
    public BrokerMessageLog findById(Long id) {
        return brokerMessageLogDao.findById(id);
    }

    @Override
    public BrokerMessageLog findByMessageId(String messageId) {
        return brokerMessageLogDao.findByMessageId(messageId);
    }

    @Override
    public Boolean create(BrokerMessageLog brokerMessageLog) {
        return brokerMessageLogDao.create(brokerMessageLog) == 1;
    }
}
