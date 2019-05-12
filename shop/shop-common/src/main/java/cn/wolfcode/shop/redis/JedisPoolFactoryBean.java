package cn.wolfcode.shop.redis;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class JedisPoolFactoryBean implements FactoryBean<JedisPool> {

        @Autowired
        private RedisConfig redisConfig;

        @Override
        public JedisPool getObject(){
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(redisConfig.getPoolMaxTotal());
            config.setMaxIdle(redisConfig.getPoolMaxIdle());
            config.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);
            JedisPool jedisPool = new JedisPool(config,redisConfig.getHost(),redisConfig.getPort(),redisConfig.getTimeout(),redisConfig.getPassword());
            return jedisPool;
    }

    @Override
    public Class<?> getObjectType() {
        return JedisPool.class;
    }
}
