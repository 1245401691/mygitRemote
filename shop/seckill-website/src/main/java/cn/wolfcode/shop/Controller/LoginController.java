package cn.wolfcode.shop.Controller;

import cn.wolfcode.shop.redis.RedisService;
import cn.wolfcode.shop.redis.UserKeyPerfix;
import cn.wolfcode.shop.result.CodeMsg;
import cn.wolfcode.shop.result.Result;
import cn.wolfcode.shop.service.IGoodsService;
import cn.wolfcode.shop.service.IUserService;
import cn.wolfcode.shop.util.CookieUtil;
import cn.wolfcode.shop.vo.LoginVo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class LoginController {
    @Reference
    private IUserService userService;
    @Reference
    private IGoodsService goodsService;
    @Autowired
    private RedisService redisService;


    /**
     * 该方法用来处理用户的登录情况
     * @param  loginVo 用户登录账号密码对象
     * @return
     */
    @RequestMapping("/login")
    public Object login(@Valid LoginVo loginVo, HttpServletResponse response, HttpServletRequest request){
        //判断手机号码是否存在
        if(userService.get(loginVo.getMobile())==null){
            return Result.error(CodeMsg.MOBILE_NO_EXIST);
        }
        System.out.println("从数据库中查询");
        String token = userService.login(loginVo.getMobile(), loginVo.getPassword());
        CookieUtil.addCookie(response,UserKeyPerfix.USER_TOKEN,token,UserKeyPerfix.USER_KEY_PERFIX.getExpireSeconds());
        return Result.success(token);
    }
}
