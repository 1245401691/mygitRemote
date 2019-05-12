package cn.wolfcode.shop.redis;


/**
 * 该类里面是用来处理用户登录的redis处理
 */
public class UserKeyPerfix extends BasePrefix {
    public final static String USER_TOKEN ="USER_TOKEN";
    public final static String USER_LOCK ="USER_LOCK";

    public  static UserKeyPerfix USER_KEY_PERFIX=new UserKeyPerfix(USER_TOKEN, 1800);

    public UserKeyPerfix(String perfix,int expireSeconds){
        super(perfix,expireSeconds);
    }

}
