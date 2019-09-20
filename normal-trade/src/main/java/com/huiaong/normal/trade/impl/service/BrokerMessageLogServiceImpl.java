package com.huiaong.normal.trade.impl.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.huiaong.normal.trade.impl.dao.BrokerMessageLogDao;
import com.huiaong.normal.trade.mq.dto.BrokerMessageLogDto;
import com.huiaong.normal.trade.mq.model.BrokerMessageLog;
import com.huiaong.normal.trade.mq.service.BrokerMessageLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Component
public class BrokerMessageLogServiceImpl implements BrokerMessageLogService {

    private final BrokerMessageLogDao brokerMessageLogDao;
    private ObjectMapper objectMapper;

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
        if (createResult != 1) {
            return null;
        }
        return brokerMessageLog;
    }

    @Override
    public BrokerMessageLog update(BrokerMessageLog brokerMessageLog) {
        int updateResult = brokerMessageLogDao.update(brokerMessageLog);
        if (updateResult != 1) {
            return null;
        }
        return brokerMessageLog;
    }

    /*
            第一次重试   1   分中后
            第二次重试   5   分中后
            第三次重试   15  分中后

     */
    @Override
    public List<BrokerMessageLogDto> findArticleOneHundredFailToSendMessage() {
        List<BrokerMessageLog> brokerMessageLogList = brokerMessageLogDao.findArticleOneHundredFailToSendMessage();
        if (CollectionUtils.isEmpty(brokerMessageLogList)) return Lists.newArrayList();
        List<BrokerMessageLogDto> brokerMessageLogDtoList = Lists.newArrayList();
        BrokerMessageLogDto brokerMessageLogDto;
        objectMapper = new ObjectMapper();
        for (BrokerMessageLog brokerMessageLog : brokerMessageLogList) {
            try {
                brokerMessageLogDto = objectMapper.readValue(brokerMessageLog.getMessage(), BrokerMessageLogDto.class);
                if (Objects.isNull(brokerMessageLogDto.getMessageId())) {
                    brokerMessageLogDto.setMessageId(brokerMessageLog.getMessageId());
                }
                brokerMessageLogDtoList.add(brokerMessageLogDto);
                buildRetryData(brokerMessageLog);
                brokerMessageLogDao.update(brokerMessageLog);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return brokerMessageLogDtoList;
    }

    @Override
    public BrokerMessageLog updateByMessageId(BrokerMessageLog brokerMessageLog) {
        int updateResult = brokerMessageLogDao.updateByMessageId(brokerMessageLog);
        if (updateResult != 1) {
            return null;
        }
        return brokerMessageLog;
    }

    private void buildRetryData(BrokerMessageLog brokerMessageLog) {
        int tryCount = brokerMessageLog.getTryCount();
        Calendar cal = Calendar.getInstance();
        int minuteAmount = Objects.equals(tryCount, 1) ? 5 : 15;
        cal.add(Calendar.MINUTE, minuteAmount);
        brokerMessageLog.setNextRetry(cal.getTime());

        brokerMessageLog.setTryCount(++tryCount);
    }
}
