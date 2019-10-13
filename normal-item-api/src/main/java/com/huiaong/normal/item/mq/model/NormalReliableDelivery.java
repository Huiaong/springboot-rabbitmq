package com.huiaong.normal.item.mq.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NormalReliableDelivery implements Serializable {
    private Long id;

    private String messageId;

    private Date createdAt;

    private Date updatedAt;

}
