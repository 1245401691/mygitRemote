package cn.wolfcode.shop.util;



import com.alibaba.dubbo.common.utils.StringUtils;

import java.util.regex.Pattern;

/**
 * 该方法用来处理手机格式
 */
public class ValidatorUtil {
    //定义手机的格式验证
    private final static Pattern MOBILE_PATTERN=Pattern.compile("1\\d{10}");

    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        //这个方法是用来判断手机格式是否符合
        return MOBILE_PATTERN.matcher(mobile).matches();
    }

}
