package cn.wolfcode.shop.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class RedisService {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 该方法用来设置redis缓存
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getKeyPrefix()+key;
            String strVal = beanToString(value);
            if(prefix.getExpireSeconds()>0){
                jedis.setex(realKey,prefix.getExpireSeconds(),strVal);
            }else{
                jedis.set(realKey,strVal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnToPool(jedis);
        }
        return true;
    }

    /**
     * 该方法用来获取redis缓存
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix,String key,Class<T> clazz){
        Jedis jedis = null;
        String strVal=null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getKeyPrefix()+key;
            strVal = jedis.get(realKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnToPool(jedis);
        }
        return stringToBean(strVal,clazz);
    }

    /**
     * 因为redis设置值的时候只能存储string类型，所以需要调用该方法进行数据类型的转换
     * @param strVal
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T stringToBean(String strVal, Class<T> clazz) {
        if(clazz==int.class || clazz == Integer.class){
            return (T) Integer.valueOf(strVal);
        }else if(clazz == float.class || clazz == Float.class){
            return (T) Float.valueOf(strVal);
        }else if(clazz==long.class || clazz==Long.class){
            return (T) Long.valueOf(strVal);
        }else if(clazz==boolean.class || clazz==Boolean.class){
            return (T) Boolean.valueOf(strVal);
        }else if(clazz == String.class){
            return (T) strVal;
        }else{
            return JSON.parseObject(strVal,clazz);
        }
    }


    /**
     * 因为redis设置值的时候只能存储string类型，所以需要调用该方法进行数据类型的转换
     * @param value
     * @param <T>
     * @return
     */
    private <T> String beanToString(T value) {
        if(value==null){
            return null;
        }
        Class clazz = value.getClass();
        if(clazz==int.class || clazz == Integer.class){
            return ""+value;
        }else if(clazz == float.class || clazz == Float.class){
            return ""+value;
        }else if(clazz==long.class || clazz==Long.class){
            return ""+value;
        }else if(clazz==boolean.class || clazz==Boolean.class){
            return String.valueOf(value);
        }else if(clazz == String.class){
            return (String) value;
        }else{
            return JSON.toJSONString(value);
        }



    }

    /**
     * 延长redis的时间
     * @param prefix
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(KeyPrefix prefix,String key,int seconds){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getKeyPrefix()+key;
            return jedis.expire(realKey,seconds);
        }finally {
            returnToPool(jedis);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis!=null){
            jedis.close();
        }
    }

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 释放分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock( String lockKey, String requestId) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        }finally {
            returnToPool(jedis);
        }

    }


    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public  boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean hset(String prefix, Long goodId, T value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix;
            String strVal = beanToString(value);
            jedis.hset(realKey,goodId+"",strVal);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnToPool(jedis);
        }
        return true;
    }


    public <T> T hget(String prefix,String filed,Class<T> clazz){
        Jedis jedis = null;
        String strVal=null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix;
            strVal = jedis.hget(realKey,filed);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnToPool(jedis);
        }
        return stringToBean(strVal,clazz);
    }

    public Map<Long,Object> hgetAll(String prefix,Class<?> clazz){
        Jedis jedis = null;
        String strVal=null;
        Map<Long,Object> map = new HashMap();
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix;
            Map<String, String> map1 = jedis.hgetAll(realKey);
            for (String goodId:map1.keySet()) {
                //得到所有的key，然后根据key得到value
                String value = map1.get(goodId);
                map.put(Long.parseLong(goodId),stringToBean(value,clazz));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnToPool(jedis);
        }
        return  map;
    }


    /**
     * 专门用来统计秒杀商品的数量的
     */
    public void setCount(Long id,Integer count) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.set(String.valueOf(id),String.valueOf(count));
        }finally{
            returnToPool(jedis);
        }
    }

    /**
     * 当抢购完成之后不在让redis进行递减，直接返回页面
     */
    public void setSign(String sign,Integer count) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.set(sign,String.valueOf(count));
        }finally{
            returnToPool(jedis);
        }
    }

    /**
     * 当抢购完成之后不在让redis进行递减，直接返回页面
     */
    public Object getSign(String sign) {
        Jedis jedis = null;
        String count=null;
        try{
            jedis = jedisPool.getResource();
            count = jedis.get(sign);
        }finally {
            returnToPool(jedis);
        }
        return count;
    }

    public Integer getCount(Long id) {
        Jedis jedis = null;
        String count=null;
        try{
            jedis = jedisPool.getResource();
            count = jedis.get(String.valueOf(id));
        }finally {
            returnToPool(jedis);
        }

        return Integer.parseInt(count);
    }

    public Long decr(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.decr(key);
        }finally {
            returnToPool(jedis);
        }
    }

    public boolean exists(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getKeyPrefix()+key;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    public Long incr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getKeyPrefix()+key;
            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }
}
