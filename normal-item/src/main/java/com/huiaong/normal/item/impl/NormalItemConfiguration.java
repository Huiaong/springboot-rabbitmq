package com.huiaong.normal.item.impl;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.huiaong.normal.item.impl.log.dao")
public class NormalItemConfiguration {

}
