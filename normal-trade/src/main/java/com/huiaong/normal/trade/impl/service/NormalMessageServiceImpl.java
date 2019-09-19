package com.huiaong.normal.trade.impl.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.huiaong.normal.trade.impl.dao.NormalMessageDao;
import com.huiaong.normal.trade.impl.manage.MessageManager;
import com.huiaong.normal.trade.mq.dto.BrokerMessageLogDto;
import com.huiaong.normal.trade.sms.service.NormalMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service
@Slf4j
public class NormalMessageServiceImpl implements NormalMessageService {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private final NormalMessageDao normalMessageDao;
    private final MessageManager messageManager;

    @Autowired
    public NormalMessageServiceImpl(NormalMessageDao normalMessageDao, MessageManager messageManager) {
        this.normalMessageDao = normalMessageDao;
        this.messageManager = messageManager;
    }

    @Override
    public Long create(BrokerMessageLogDto brokerMessageLogDto) {

        return messageManager.create(brokerMessageLogDto);
    }

}
