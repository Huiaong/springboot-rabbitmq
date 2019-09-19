package com.huiaong.normal.trade.sms.vo;

import com.huiaong.normal.trade.sms.enums.NormalMessagePlatform;
import lombok.Data;

import java.io.Serializable;

@Data
public class NormalMessageVO implements Serializable {

    private String message;

    /**
     * @see NormalMessagePlatform
     */
    private Integer platform;

    private Long sendTo;
}
