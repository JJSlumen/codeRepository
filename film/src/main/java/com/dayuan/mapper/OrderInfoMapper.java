package com.dayuan.mapper;

import com.dayuan.entity.OrderInfo;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer id);

    void insertOrderInfo(OrderInfo orderInfo);
}