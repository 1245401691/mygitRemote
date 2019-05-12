package cn.wolfcode.shop.util;

import cn.wolfcode.shop.domain.User;
import cn.wolfcode.shop.redis.RedisService;
import cn.wolfcode.shop.redis.SeckillGoodsKeyPerfix;
import cn.wolfcode.shop.redis.UserKeyPerfix;
import cn.wolfcode.shop.result.CodeMsg;
import cn.wolfcode.shop.util.AccessLimit;
import cn.wolfcode.shop.util.CookieUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否是后台的请求方法
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            //判断请求方法上面是否贴了该注解
            AccessLimit al = hm.getMethodAnnotation(AccessLimit.class);
            if(al==null){
                return true;
            }
            //判断用户是否需要登陆
            boolean b = al.needLogin();
            if(b){
                User user = getUser(request, response);
                if(user==null){
                    renderResponse(response,CodeMsg.LOGIN_FAIL);
                    return false;
                }

            }
            //从redies中获取之前的访问次数，如果访问次数超过指定次数，拒绝访问
            int maxCount = al.maxCount();
            int seconds = al.seconds();

            if(maxCount>0&&seconds>0){
                Long incr;
                String ip = request.getRemoteAddr();
                String url = request.getRequestURI();
                if(redisService.exists(SeckillGoodsKeyPerfix.ACCESSLIMIT,ip+":"+url)){
                    incr = redisService.incr(SeckillGoodsKeyPerfix.ACCESSLIMIT, ip + ":" + url);
                    redisService.expire(SeckillGoodsKeyPerfix.ACCESSLIMIT,ip+":"+url,maxCount);
                }else{
                    incr = redisService.incr(SeckillGoodsKeyPerfix.ACCESSLIMIT, ip + ":" + url);
                }

                if(incr>seconds){
                    renderResponse(response,CodeMsg.DO_SECKIILL_ALL);
                    return false;
                }
            }
        }

        return true;
    }

    public void renderResponse(HttpServletResponse response, CodeMsg codeMsg){
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(JSON.toJSONString(codeMsg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  User getUser(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        User user=null;
        String token=null;
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                token = cookie.getValue();
                if (cookie.getName().equals(UserKeyPerfix.USER_TOKEN)) {
                    user = redisService.get(UserKeyPerfix.USER_KEY_PERFIX, token, User.class);
                    if(user!=null){
                        //延长redis的缓存时间
                        redisService.expire(UserKeyPerfix.USER_KEY_PERFIX, token, UserKeyPerfix.USER_KEY_PERFIX.getExpireSeconds());
                        //延长cookie的时间
                        CookieUtil.addCookie(response, UserKeyPerfix.USER_TOKEN, token, UserKeyPerfix.USER_KEY_PERFIX.getExpireSeconds());
                        return user;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
