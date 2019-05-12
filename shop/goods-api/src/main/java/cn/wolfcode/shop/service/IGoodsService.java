package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Goods;

import java.util.List;

public interface IGoodsService {
    List<Goods> selectByIds(List<Long> ids);

    Goods get(Long goodsId);
}
