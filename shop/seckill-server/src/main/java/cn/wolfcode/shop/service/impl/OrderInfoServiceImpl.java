package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.GoodsVo;
import cn.wolfcode.shop.domain.OrderInfo;
import cn.wolfcode.shop.mapper.OrderInfoMapper;
import cn.wolfcode.shop.service.IOrderInfoService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class OrderInfoServiceImpl implements IOrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Override
    public void createOrder(OrderInfo orderInfo) {

        orderInfoMapper.insert(orderInfo);
    }

    @Override
    public OrderInfo get(Long id, Long goodId) {
        return orderInfoMapper.get(id,goodId);
    }


}
