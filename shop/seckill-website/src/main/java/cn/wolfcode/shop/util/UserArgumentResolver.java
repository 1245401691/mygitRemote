package cn.wolfcode.shop.util;

import cn.wolfcode.shop.domain.User;
import cn.wolfcode.shop.redis.RedisService;
import cn.wolfcode.shop.redis.UserKeyPerfix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private RedisService redisService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
            return parameter.getParameterType()== User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();
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
}
