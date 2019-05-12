package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.SeckillOrder;

public interface ISeckillOrderService {
    SeckillOrder get(Long goodId,Long userId);

    void createSeckillOrder(SeckillOrder seckillOrder);
}
