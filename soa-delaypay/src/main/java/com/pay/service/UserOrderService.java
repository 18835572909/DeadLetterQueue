package com.pay.service;

import com.pay.common.APIResponse;
import com.pay.entity.UserOrder;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
public interface UserOrderService {

    APIResponse userOrderPush(UserOrder userOrder);

}
