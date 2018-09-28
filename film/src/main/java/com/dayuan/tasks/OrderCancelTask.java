package com.dayuan.tasks;

import com.dayuan.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderCancelTask {

    private static Logger log = LoggerFactory.getLogger(OrderCancelTask.class);

    @Autowired
    OrderService orderService;

    @Scheduled(fixedRate = 1000*60)
    public void cancelOrder() {
        log.info("自动取消订单开始");
        orderService.cancelOrder();
        log.info("自动取消订单结束");
    }
}
