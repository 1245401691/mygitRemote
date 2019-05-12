package cn.wolfcode.shop.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CodeMsg implements Serializable{
    private int code;
    private String msg;
    private CodeMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
    //系统模块
    public static CodeMsg SUCCESS = new CodeMsg(200,"success");
    //校验异常
    public static CodeMsg PARAM_ERROR=new CodeMsg(5000005,"参数异常:%s");
    //系统异常
    public static CodeMsg SYSTEM_EXCEPTION=new CodeMsg(5000000,"系统异常");
    //手机号码为空
    public static CodeMsg MOBILE_EMPTY=new CodeMsg(5000001,"手机号码不能为空");
    //手机号码格式不正确
    public static CodeMsg MOBILE_FORMAT=new CodeMsg(5000002,"手机号码格式不正确");
    //用户名不存在
    public static CodeMsg MOBILE_NO_EXIST=new CodeMsg(5000003,"手机号码不存在");
    //密码不正确
    public static CodeMsg PASSWORD_FALSE=new CodeMsg(5000004,"密码不正确");
    //密码不能为空
    public static CodeMsg PASSWORD_NO_EXIST=new CodeMsg(5000006,"密码不能为空");
    //没有登录
    public static CodeMsg LOGIN_FAIL = new CodeMsg(5000007,"请登录后在操作");
    //秒杀还没开始
    public static CodeMsg SECKILL_NOT_START = new CodeMsg(5000008,"请等待秒杀开始之后在操作");
    //秒杀结束，商品已经全部被抢购
    public static CodeMsg SECKILL_NOT_GOODS = new CodeMsg(5000009,"秒杀结束，商品已经全部被抢购");
    //请勿重复下单
    public static CodeMsg SECKILL_ORDER_NOT_ONE = new CodeMsg(50000010,"请勿重复下单");
    public static CodeMsg VERIFYCODE_INVALID = new CodeMsg(50000011,"验证码无效，请重新输入验证码");
    public static CodeMsg DO_SECKIILL_ALL = new CodeMsg(50000012,"请勿频繁发送");


    public CodeMsg fillArgs(Object ... args){
        return new CodeMsg(this.code,String.format(this.msg,args));
    }
}
