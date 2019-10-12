package com.huiaong.normal.trade.impl;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({
        "com.huiaong.normal.trade.impl.log.dao",
        "com.huiaong.normal.trade.impl.mq.dao",
        "com.huiaong.normal.trade.impl.order.dao"
})
public class NormalTradeConfiguration {

}
