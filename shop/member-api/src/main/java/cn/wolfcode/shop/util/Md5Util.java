package cn.wolfcode.shop.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 该类用户进行密码加密的
 */
public class Md5Util {
    private final  static  String salt="1a2b3c4" ;
    /**
     * 该方法用来处理用户注册时候的加密
     * @param value
     * @return
     */
    public static String inputPassToFormPass(String value){
        return DigestUtils.md2Hex(""+salt.charAt(0)+salt.charAt(1)+value+salt.charAt(4)+salt.charAt(5));
    }


    /**
     * 该方法用来处理在后台对密码进行二次加密，然后存入到数据库中
     * @param value
     * @param salt
     * @return
     */
    public static String formPassToDbPass(String value,String salt){
        return DigestUtils.md2Hex(""+salt.charAt(0)+salt.charAt(1)+value+salt.charAt(4)+salt.charAt(5));
    }

    /**
     *这个方法是为了获取第一个用户进行二次加密后的密码，直接手动存入数据库方便后面的测试
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(formPassToDbPass("01b621bbe0333a39baa00e7822eeaa8d","4c3b2a1"));
    }

}
