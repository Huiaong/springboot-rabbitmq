package com.huiaong.normal.trade.mq.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BrokerMessageLog implements Serializable {

    private Long id;

    private String messageId;

    private String message;

    private int tryCount;

    /**
     * @see com.huiaong.normal.trade.mq.enums.BrokerMessageStatus
     */
    private int status;

    private Date nextRetry;

    private Date createTime;

    private Date updateTime;

}
