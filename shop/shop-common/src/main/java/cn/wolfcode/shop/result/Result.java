package cn.wolfcode.shop.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    private int code;
    private String msg;
    private T data;
    private Result(int code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static <T> Result<T> success(T data){
        return new Result(200,"success",data);
    }
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<>(codeMsg.getCode(),codeMsg.getMsg(),null);
    }
}
