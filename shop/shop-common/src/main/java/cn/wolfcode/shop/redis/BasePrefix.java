package cn.wolfcode.shop.redis;

import lombok.Getter;
import lombok.Setter;

/**
 * 该类实现前缀接口，用来处理不同server中的前缀
 */
@Getter
@Setter
public class BasePrefix implements KeyPrefix {
    private String perfix;
    private int expireSeconds;
    public BasePrefix(){};

    public BasePrefix(String perfix,int expireSeconds){
        this.perfix=perfix;
        this.expireSeconds=expireSeconds;
    }

    @Override
    public String getKeyPrefix() {
        return this.getClass().getSimpleName()+":"+perfix;
    }

    @Override
    public int getExpireSeconds() {
        return this.expireSeconds;
    }
}
