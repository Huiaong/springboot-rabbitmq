package com.huiaong.normal.trade.sms.model;

import com.huiaong.normal.trade.sms.enums.NormalMessagePlatform;
import lombok.Data;

import java.io.Serializable;

@Data
public class NormalMessage  implements Serializable {

    private Long id;

    private String message;

    /**
     * @see NormalMessagePlatform
     */
    private Integer platform;

    private Long sendTo;

    private Data createTime;

    private Data updateTime;

}
