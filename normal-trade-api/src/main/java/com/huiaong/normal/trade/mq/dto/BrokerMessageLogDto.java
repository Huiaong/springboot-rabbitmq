package com.huiaong.normal.trade.mq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrokerMessageLogDto implements Serializable {

    private Long messageId;

    private String message;

    private String exchangeName;

    private String routingKey;

    /**
     * @see com.huiaong.normal.trade.sms.enums.NormalMessagePlatform
     */
    private Integer platform;

    private Long sendTo;
}
