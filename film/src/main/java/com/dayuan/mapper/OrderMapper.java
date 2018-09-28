package com.dayuan.mapper;

import com.dayuan.entity.Order;
import org.apache.ibatis.annotations.Param;

import javax.naming.InsufficientResourcesException;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<String> listSaledSeats(@Param("showTimeId") Integer showTimeId);
    void insertOrder(Order order);

    Order selectByOrderNo(@Param("orderNo") String orderNo);

    void updateStatus(@Param("orderNo") String orderNo,
                      @Param("tradeNo") String tradeNo);

    Order selectByPrimaryKeyForLock(Integer id);

    void updateStatusById(Integer id);

    List<Integer> listCanceledOrders();

    Integer countBySeat(@Param("seat") String seat,
                        @Param("showTimeId") int showTimeId);
}