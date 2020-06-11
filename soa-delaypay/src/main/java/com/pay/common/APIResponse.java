package com.pay.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {

    private int code;
    private String message;
    private Object data;

    public static APIResponse newInstance(int code,String message){
        return new APIResponse(code,message,null);
    }

    public static APIResponse newInstance(int code,String message,Object data){
        return new APIResponse(code,message,data);
    }

}
