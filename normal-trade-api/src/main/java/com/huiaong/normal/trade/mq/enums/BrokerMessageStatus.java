package com.huiaong.normal.trade.mq.enums;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public enum BrokerMessageStatus {
    WAIT_SEND(0, "待投递"),
    HAS_SEND(1, "投递完成"),
    FAIL_SEND(2, "投递失败");

    private Integer value;

    private String desc;

    public static BrokerMessageStatus fromValue(int value) {
        for (BrokerMessageStatus status : BrokerMessageStatus.values()) {
            if (Objects.equals(status.value, value)) {
                return status;
            }
        }
        return null;
    }

    public Integer value() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }

}
