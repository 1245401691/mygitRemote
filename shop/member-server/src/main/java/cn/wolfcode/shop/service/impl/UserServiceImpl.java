package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.User;
import cn.wolfcode.shop.exception.GobalException;
import cn.wolfcode.shop.mapper.UserMapper;
import cn.wolfcode.shop.redis.GoodUserKeyperfix;
import cn.wolfcode.shop.redis.RedisService;
import cn.wolfcode.shop.redis.UserKeyPerfix;
import cn.wolfcode.shop.result.CodeMsg;
import cn.wolfcode.shop.service.IUserService;

import cn.wolfcode.shop.util.Md5Util;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;
    @Override
    public User get(String mobile) {
        return userMapper.getById(mobile);
    }

    @Override
    public String login(String mobile, String password) {
        String token = getToken();
        boolean lock = redisService.tryGetDistributedLock(UserKeyPerfix.USER_LOCK, mobile, 5);
        if(!lock){
            return null;
        }
        User user = redisService.get(GoodUserKeyperfix.GOODS_USER_GETBYID,mobile, User.class);
        if(user!=null){
            if(user.getId()!=null){
                //将值存放进redis中
                redisService.set(UserKeyPerfix.USER_KEY_PERFIX, token,user);
                System.out.println("从缓存中获取");
                return token;
            }else{
                return null;
            }
        }
        user = userMapper.getById(mobile);
        //将值存放进redis中
        redisService.set(UserKeyPerfix.USER_KEY_PERFIX, token,user);
        //在进行查询用户进行后面的操作的时候，要准备加锁。防止并发时间
        //对密码要进行加盐处理，然后在进行比较
        String password1 = Md5Util.formPassToDbPass(password, user.getSalt());
        if(!user.getPassword().equals(password1)){
            throw new GobalException(CodeMsg.PASSWORD_FALSE);
        }
        //将用户的信息存放进redis
        if(user!=null){
            redisService.set(GoodUserKeyperfix.GOODS_USER_GETBYID,String.valueOf(mobile),user);
        }else{
            redisService.set(GoodUserKeyperfix.GOODS_USER_GETBYID_NULL,String.valueOf(mobile),new User());
        }

        //操作完成之后释放锁
        redisService.releaseDistributedLock(UserKeyPerfix.USER_LOCK,mobile);
        return token;
    }

    public String getToken(){
        String token = UUID.randomUUID().toString().replace("-","");
        return token;
    }
}
