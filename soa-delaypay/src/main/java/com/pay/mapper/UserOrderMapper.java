package com.pay.mapper;

import com.pay.entity.UserOrder;
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
public interface UserOrderMapper {

    long insertSelective(UserOrder userOrder);

    UserOrder selectByIdAndStatus(@Param("status") int status,@Param("id") long id);

    long updateOrderActiveById(@Param("id") long id,@Param("isActive") int isActive);
}
