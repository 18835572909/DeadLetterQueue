package com.pay.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Data
@ToString
public class User {
    private Long userId;
    private String username;
    private String password;
    private String cnName;
}
