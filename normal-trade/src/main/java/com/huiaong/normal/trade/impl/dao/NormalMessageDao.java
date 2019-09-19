package com.huiaong.normal.trade.impl.dao;

import com.huiaong.normal.trade.sms.model.NormalMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface NormalMessageDao {
    int create(NormalMessage normalMessage);
}
