package com.huiaong.normal.trade.mq.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TradeMQResponse implements Serializable {

    private Long id;

    private String messageId;

    //内容
    private String content;

    //交换机名
    private String exchange;

    //路由键名
    private String routingKey;

    /**
     * 消息发送状态
     * @see com.huiaong.normal.trade.mq.enums.TradeMQResponseStatus
     */
    private Integer status;

    //重试次数
    private Integer retryCount;

    //下次重试时间
    private Date nextRetry;

    private Date createdAt;

    private Date updatedAt;

}
