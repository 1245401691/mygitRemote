package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.SeckillOrder;
import cn.wolfcode.shop.mapper.SeckillOrderMapper;
import cn.wolfcode.shop.service.ISeckillOrderService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SeckillOrderServiceImpl implements ISeckillOrderService {
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    /**
     * 该方法用来查询订单
     * @param goodId
     * @return
     */
    @Override
    public SeckillOrder get(Long goodId,Long userId) {
        return seckillOrderMapper.get(goodId,userId);
    }

    /**
     * 该方法用来创建用户秒杀订单
     * @param seckillOrder
     */
    @Override
    public void createSeckillOrder(SeckillOrder seckillOrder) {
        seckillOrderMapper.insert(seckillOrder);
    }
}
