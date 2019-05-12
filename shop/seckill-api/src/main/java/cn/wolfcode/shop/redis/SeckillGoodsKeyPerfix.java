package cn.wolfcode.shop.redis;


/**
 * 该类里面是用来处理秒杀商品的redis处理
 */
public class SeckillGoodsKeyPerfix extends BasePrefix {
    public final static String SECKILL_GOODS ="SeckillGoods";
    public final static String SECKILL_GOODS_LIST ="SeckillGoodsList";
    public final static String SECKILL_ORDER ="SeckillOrder";

    public  static SeckillGoodsKeyPerfix SECKILL_GOOD_PAGE=new SeckillGoodsKeyPerfix(SECKILL_GOODS, 10);
    public  static SeckillGoodsKeyPerfix SECKILL_GOOD_ORDER=new SeckillGoodsKeyPerfix(SECKILL_ORDER, 15*60);
    public  static SeckillGoodsKeyPerfix SECKILL_PATH=new SeckillGoodsKeyPerfix("seckillPath", 5);
    public  static SeckillGoodsKeyPerfix VERIFYCODE=new SeckillGoodsKeyPerfix("verifyCode", 180);
    public  static SeckillGoodsKeyPerfix ACCESSLIMIT=new SeckillGoodsKeyPerfix("accessLimit", 5);


    public SeckillGoodsKeyPerfix(String perfix, int expireSeconds){
        super(perfix,expireSeconds);
    }
}
