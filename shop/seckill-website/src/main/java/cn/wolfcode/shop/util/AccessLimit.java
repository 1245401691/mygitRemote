package cn.wolfcode.shop.util;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {
    public int seconds() default -1;
    public int maxCount() default -1;
    public boolean needLogin() default false;
}
