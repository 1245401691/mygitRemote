package cn.wolfcode.shop.service;


import cn.wolfcode.shop.domain.GoodsVo;
import cn.wolfcode.shop.domain.OrderInfo;

import java.util.List;

public interface ISeckillService {

    List<GoodsVo> selectAll();

    GoodsVo selectById(Long goodsId);

    void update(GoodsVo goodsVo);

    OrderInfo seckillOrder(Long goodId, Long userId);
}
