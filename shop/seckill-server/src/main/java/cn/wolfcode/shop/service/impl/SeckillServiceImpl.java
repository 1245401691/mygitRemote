package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.*;
import cn.wolfcode.shop.exception.GobalException;
import cn.wolfcode.shop.mapper.SeckillMapper;
import cn.wolfcode.shop.redis.RedisService;
import cn.wolfcode.shop.redis.SeckillGoodsKeyPerfix;
import cn.wolfcode.shop.service.IGoodsService;
import cn.wolfcode.shop.service.IOrderInfoService;
import cn.wolfcode.shop.service.ISeckillOrderService;
import cn.wolfcode.shop.service.ISeckillService;
import cn.wolfcode.shop.util.IdGenerateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SeckillServiceImpl implements ISeckillService {
    @Autowired
    private SeckillMapper seckillMapper;
    @Reference
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderInfoService orderInfoService;
    @Autowired
    private RedisService redisService;

    public List<GoodsVo> selectAll() {
        List<Seckill> seckills = seckillMapper.selectAll();
        Map<Long,Seckill> map = new HashMap<>();
        for (Seckill se:seckills) {
            map.put(se.getGoodId(),se);
        }
        //获取到所有的秒杀商品id
        List<Long> ids = new ArrayList<>(map.keySet());
        List<Goods> goods = goodsService.selectByIds(ids);
        List<GoodsVo> goodsVos = new ArrayList<>();
        for (Goods go:goods) {
            GoodsVo goodVo = new GoodsVo();
            goodVo.setId(go.getId());
            goodVo.setStartDate(map.get(go.getId()).getStartDate());
            goodVo.setEndDate((map.get(go.getId()).getEndDate()));
            goodVo.setGoodName(go.getGoodName());
            goodVo.setGoodImg(go.getGoodImg());
            goodVo.setGoodPrice(go.getGoodPrice());
            goodVo.setSeckillPrice(map.get(go.getId()).getSeckillPrice());
            goodVo.setStockCount(map.get(go.getId()).getStockCount());
            goodsVos.add(goodVo);
        }
        return goodsVos;
    }

    @Override
    public GoodsVo selectById(Long goodsId) {
        Seckill seckill = seckillMapper.get(goodsId);
        Goods goods = goodsService.get(goodsId);
        GoodsVo goodVo = new GoodsVo();
        goodVo.setId(goodsId);
        goodVo.setGoodName(goods.getGoodName());
        goodVo.setGoodImg(goods.getGoodImg());
        goodVo.setGoodPrice(goods.getGoodPrice());
        goodVo.setSeckillPrice(seckill.getSeckillPrice());
        goodVo.setStockCount(seckill.getStockCount());
        goodVo.setStartDate(seckill.getStartDate());
        goodVo.setEndDate(seckill.getEndDate());
        return goodVo;
    }

    /**
     * 该方法用来处理商品的抢购，库存的减少
     * @param goodsVo
     */
    @Override
    public void update(GoodsVo goodsVo) {
        seckillMapper.update(goodsVo);
    }

    @Override
    @Transactional
    public OrderInfo seckillOrder(Long goodId,  Long userId) {
        GoodsVo goodsVo =selectById(goodId);
        if(goodsVo.getStockCount()>0){
            //  4.5 库存数量-1
            update(goodsVo);
//        4.6 创建订单OrderInfo
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderNo(String.valueOf(IdGenerateUtil.get().nextId()));
            orderInfo.setUserId(userId);
            orderInfo.setGoodId(goodId);
            orderInfo.setGoodImg(goodsVo.getGoodImg());
            orderInfo.setGoodName(goodsVo.getGoodName());
            orderInfo.setGoodPrice(goodsVo.getGoodPrice());
            orderInfo.setSeckillPrice(goodsVo.getSeckillPrice());
            orderInfo.setCreateDate(new Date());
            orderInfoService.createOrder(orderInfo);
//        4.7 创建秒杀订单SeckillOrder
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setGoodId(goodId);
            seckillOrder.setUserId(userId);
            seckillOrder.setOrderNo(orderInfo.getOrderNo());
            seckillOrderService.createSeckillOrder(seckillOrder);
            //将秒杀订单储存起来
            redisService.set(SeckillGoodsKeyPerfix.SECKILL_GOOD_ORDER,goodId+":"+userId,orderInfo);
            return orderInfo;
        }else{
            throw new RuntimeException("已经抢购完成了");
        }
    }
}
