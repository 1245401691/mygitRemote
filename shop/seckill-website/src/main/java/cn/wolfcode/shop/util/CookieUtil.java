package cn.wolfcode.shop.util;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    /**
     * 设置cookie
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param expireSeconds
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, Integer expireSeconds){
        Cookie cookie = new Cookie(cookieName,cookieValue);
        if(expireSeconds!=null){
            cookie.setMaxAge(expireSeconds);
        }
        response.addCookie(cookie);
    }
}
