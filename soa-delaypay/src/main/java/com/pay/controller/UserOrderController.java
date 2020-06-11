package com.pay.controller;

import com.pay.common.APIResponse;
import com.pay.entity.UserOrder;
import com.pay.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */

@RestController
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    @PostMapping(value = "userOrder/push",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse userOrderPush(@RequestBody @Validated UserOrder userOrder, BindingResult result){
        if(result.hasErrors()){
            return APIResponse.newInstance(1,"参数错误！");
        }
        return userOrderService.userOrderPush(userOrder);
    }

}
