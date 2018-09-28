package com.dayuan.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.dayuan.alipay.AlipayConfig;
import com.dayuan.entity.Order;
import com.dayuan.mapper.MovieMapper;
import com.dayuan.mapper.OrderInfoMapper;
import com.dayuan.mapper.OrderMapper;
import com.dayuan.service.OrderService;
import com.dayuan.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    MovieMapper movieMapper;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "listSaledSeats.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public AjaxResult listSaledSeats(@RequestParam("showTimeId") Integer showTimeId) {
        List<String> saledSeats = orderMapper.listSaledSeats(showTimeId);
        return AjaxResult.success(saledSeats);
    }

    @RequestMapping(value = "insertOrder.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public AjaxResult insertOrder(@RequestParam("showTimeId") Integer showTimeId,
                                  @RequestParam("movieId") Integer movieId,
                                  @RequestParam("selectedSeats[]") List<String> selectedSeats,
                                  HttpSession sessin) {
        Order order = null;
        try {
            order = orderService.insertOrder(showTimeId, movieId, selectedSeats, 6217);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }

        try {
            String result = orderService.toPay(order.getOrderNo(), order.getTotalPrice());
            sessin.setAttribute("order", order);
            return AjaxResult.success(result);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            AjaxResult.fail("支付失败");
        }
        return null;
    }


    @RequestMapping(value = "notifyUrl.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String notifyUrl(HttpServletRequest request, HttpServletResponse response) {
        //接收报文


        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if (signVerified) {//验证成功
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");

            //支付宝交易号
            String trade_no = request.getParameter("trade_no");

            //交易状态
            String trade_status = request.getParameter("trade_status");

            String total_amount = request.getParameter("total_amount");

            if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                //        1:更改订单状态
                //        2:加余额

                try {
                    orderService.updateStatus(out_trade_no, trade_no, total_amount);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    response.getWriter().println("success");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {//验证失败
                try {
                    response.getWriter().println("fail");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }
}
