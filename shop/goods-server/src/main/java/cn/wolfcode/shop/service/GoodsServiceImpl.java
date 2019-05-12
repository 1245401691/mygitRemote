package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Goods;
import cn.wolfcode.shop.mapper.GoodsMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

import java.util.List;

@Service
public class GoodsServiceImpl implements IGoodsService{
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public List<Goods> selectByIds(List<Long> ids) {
        return goodsMapper.selectByIds(ids);
    }

    @Override
    public Goods get(Long goodsId) {
        return goodsMapper.get(goodsId);
    }
}
