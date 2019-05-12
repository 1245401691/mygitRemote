package cn.wolfcode.shop.redis;

/**
 * 该接口用来获取到redies中key的前缀
 */
public interface KeyPrefix {
    /**
     * 获取到前缀
     * @return
     */
    String  getKeyPrefix();

    int getExpireSeconds();
}
