package com.dayuan.controller;

import com.dayuan.entity.Order;
import com.dayuan.util.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("api/query")
public class QueryController {



    @RequestMapping(value = "queryTrade.do", method = {RequestMethod.GET, RequestMethod.POST})
    public AjaxResult queryTrade(HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        String orderNo = order.getOrderNo();
        String message = "支付成功，订单号为：" + orderNo;
        return AjaxResult.success(message);
    }
}
