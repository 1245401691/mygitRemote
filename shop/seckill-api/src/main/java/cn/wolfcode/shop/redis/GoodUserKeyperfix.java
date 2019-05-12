package cn.wolfcode.shop.redis;


/**
 * 该类里面是用来处理用户对象的redis处理
 */
public class GoodUserKeyperfix extends BasePrefix {
    public final static String GOODS_USER ="GOODS_USER";

    public  static GoodUserKeyperfix GOODS_USER_GETBYID=new GoodUserKeyperfix(GOODS_USER, 30*24*3600);
    public  static GoodUserKeyperfix GOODS_USER_GETBYID_NULL=new GoodUserKeyperfix(GOODS_USER, 5);

    public GoodUserKeyperfix(String perfix, int expireSeconds){
        super(perfix,expireSeconds);
    }

}
