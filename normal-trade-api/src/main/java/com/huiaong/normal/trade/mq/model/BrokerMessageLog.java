package com.huiaong.normal.trade.mq.model;

import lombok.Data;

import java.util.Date;

@Data
public class BrokerMessageLog {

    private Long id;

    private String messageId;

    private String message;

    private int tryCount;

    private int status;

    private Date nextRetry;

    private Date createTime;

    private Date updateTime;

}
