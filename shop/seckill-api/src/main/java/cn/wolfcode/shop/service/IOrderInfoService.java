package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.OrderInfo;

import java.util.List;

public interface IOrderInfoService {

    void createOrder(OrderInfo orderInfo);

    OrderInfo get(Long id, Long goodId);

}
