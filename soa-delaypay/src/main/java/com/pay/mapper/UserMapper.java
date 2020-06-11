package com.pay.mapper;

import com.pay.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Mapper
@Repository
public interface UserMapper {

    public User selectById(@Param("userId") Long userId);

}
