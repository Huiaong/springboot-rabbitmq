package com.huiaong.normal.trade.impl;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.huiaong.normal.trade.impl.dao")
public class NormalTradeConfiguration {

}
