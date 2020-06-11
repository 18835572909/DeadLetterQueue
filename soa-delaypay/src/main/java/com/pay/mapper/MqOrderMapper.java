package com.pay.mapper;

import com.pay.entity.MqOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Mapper
@Repository
public interface MqOrderMapper {
    long insertSelective(MqOrder order);
}
