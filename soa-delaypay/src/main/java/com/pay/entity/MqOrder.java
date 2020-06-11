package com.pay.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Data
public class MqOrder {
    private Long id;
    private Long orderId;
    private Date businessTime;
    private String memo;
}
