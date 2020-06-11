package com.pay.entity;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Data
@ToString
public class UserOrder {
    private Long id;
    @NotNull(message = "订单编号不能为空！")
    private String orderNo;
    @NotBlank(message = "用户id不能为空！")
    private String userId;
    private Integer status;
    private Integer isActive;
    private Date createTime;
    private Date updateTime;
}
