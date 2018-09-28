package com.dayuan.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.dayuan.alipay.AlipayConfig;
import com.dayuan.entity.Movie;
import com.dayuan.entity.Order;
import com.dayuan.entity.OrderInfo;
import com.dayuan.mapper.MovieMapper;
import com.dayuan.mapper.OrderInfoMapper;
import com.dayuan.mapper.OrderMapper;
import com.dayuan.util.MyRandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    MovieMapper movieMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public Order insertOrder(Integer showTimeId, Integer movieId, List<String> selectedSeats, Integer userId) throws Exception {

        for (String seat :
                selectedSeats) {
            Integer c = orderMapper.countBySeat(seat, showTimeId);
            if (c>0) {
                throw new Exception("座位已售出，请重新选择");
            }
        }

        Movie movie = movieMapper.selectByPrimaryKey(movieId);
        String price = movie.getPrice();
        BigDecimal totalPrice = BigDecimal.valueOf(Double.valueOf(price)).multiply(BigDecimal.valueOf(Double.valueOf(selectedSeats.size())));

        totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        Order order = new Order();
        order.setOrderNo(System.currentTimeMillis() + MyRandomUtil.getRandom(6));
        order.setSeatCode(MyRandomUtil.getRandom(6));
        order.setShowTimeId(showTimeId);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice.toString());
        orderMapper.insertOrder(order);

        for (String seat : selectedSeats) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setPrice(price);
            orderInfo.setOrderId(order.getId());
            orderInfo.setSeat(seat);
            orderInfoMapper.insertOrderInfo(orderInfo);
        }
        return order;
    }

    public String toPay(String orderId, String money) throws AlipayApiException {
        //调用支付宝支付接口
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderId;
        //付款金额，必填
        String total_amount = money;
        //订单名称，必填
        String subject = "大猿atm";
        //商品描述，可空
        String body = "充值";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");


        //请求

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return result;
    }

    public void updateStatus(String orderId, String tradeNo, String total_amount) throws Exception {

        if (orderId == null) {
            throw new Exception("订单有误");
        }

        Order order = orderMapper.selectByOrderNo(orderId);

        if (!order.getTotalPrice().equals(total_amount)) {
            throw new Exception("订单金额有误");
        }

        //幂等
        if (order.getStatus() == 4) {
            return;
        }

        orderMapper.updateStatus(orderId, tradeNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder() {
        List<Integer> ids = orderMapper.listCanceledOrders();
        for (Integer id :
                ids) {
            orderMapper.selectByPrimaryKeyForLock(id);
            orderMapper.updateStatusById(id);
        }
    }
}
