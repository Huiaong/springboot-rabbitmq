package com.huiaong.normal.trade.impl.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.huiaong.normal.trade.impl.dao.BrokerMessageLogDao;
import com.huiaong.normal.trade.mq.dto.BrokerMessageLogDto;
import com.huiaong.normal.trade.mq.enums.BrokerMessageStatus;
import com.huiaong.normal.trade.mq.model.BrokerMessageLog;
import com.huiaong.normal.trade.mq.service.BrokerMessageLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Service
@Component
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
    public BrokerMessageLog create(BrokerMessageLog brokerMessageLog) {
        int createResult = brokerMessageLogDao.create(brokerMessageLog);
        if (createResult != 1){
            return null;
        }
        return brokerMessageLog;
    }
}
