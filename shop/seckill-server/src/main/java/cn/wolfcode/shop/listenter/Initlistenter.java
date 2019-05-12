package cn.wolfcode.shop.listenter;

import cn.wolfcode.shop.domain.GoodsVo;
import cn.wolfcode.shop.redis.RedisService;
import cn.wolfcode.shop.redis.SeckillGoodsKeyPerfix;
import cn.wolfcode.shop.service.IOrderInfoService;
import cn.wolfcode.shop.service.ISeckillOrderService;
import cn.wolfcode.shop.service.ISeckillService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/*秒杀商品列表缓存*/
@Component
public class Initlistenter implements ApplicationListener<ApplicationEvent>{
    @Autowired
    private RedisService redisService;
    @Autowired
    private ISeckillService seckillService;
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        //获取到所有的秒杀商品
        List<GoodsVo> goodsVos = seckillService.selectAll();
        //存放到redis里面
        for (GoodsVo goodsVo : goodsVos) {
            redisService.setCount(goodsVo.getId(),goodsVo.getStockCount());
            redisService.hset(SeckillGoodsKeyPerfix.SECKILL_GOODS_LIST,goodsVo.getId(),goodsVo);
        }
    }
}
