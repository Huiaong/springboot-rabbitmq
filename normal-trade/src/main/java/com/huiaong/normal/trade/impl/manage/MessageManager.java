package com.huiaong.normal.trade.impl.manage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiaong.normal.trade.impl.dao.BrokerMessageLogDao;
import com.huiaong.normal.trade.impl.dao.NormalMessageDao;
import com.huiaong.normal.trade.mq.dto.BrokerMessageLogDto;
import com.huiaong.normal.trade.mq.enums.BrokerMessageStatus;
import com.huiaong.normal.trade.mq.model.BrokerMessageLog;
import com.huiaong.normal.trade.mq.service.BrokerMessageLogService;
import com.huiaong.normal.trade.sms.model.NormalMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class MessageManager {

    private final NormalMessageDao normalMessageDao;
    private final BrokerMessageLogDao brokerMessageLogDao;
    private final ObjectMapper objectMapper;

    {
        objectMapper = new ObjectMapper();
    }

    @Autowired
    public MessageManager(NormalMessageDao normalMessageDao, BrokerMessageLogDao brokerMessageLogDao) {
        this.normalMessageDao = normalMessageDao;
        this.brokerMessageLogDao = brokerMessageLogDao;
    }


    @Transactional(rollbackFor = Exception.class)
    public Long create(BrokerMessageLogDto brokerMessageLogDto){
        NormalMessage normalMessage = new NormalMessage();
        BeanUtils.copyProperties(brokerMessageLogDto, normalMessage);

        normalMessageDao.create(normalMessage);


        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setMessageId(normalMessage.getId());
        brokerMessageLog.setStatus(BrokerMessageStatus.WAIT_SEND.value());

        brokerMessageLogDto.setMessageId(normalMessage.getId());
        String json = null;
        try {
            json = objectMapper.writeValueAsString(brokerMessageLogDto);
        } catch (JsonProcessingException e) {
            log.error("write to json string error:" + brokerMessageLog, e);
        }

        brokerMessageLog.setMessage(json);
        brokerMessageLogDao.create(brokerMessageLog);

        return brokerMessageLog.getId();
    }
}
