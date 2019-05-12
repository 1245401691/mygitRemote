package cn.wolfcode.shop.exception;

import cn.wolfcode.shop.result.CodeMsg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GobalException extends RuntimeException {
    private CodeMsg codeMsg;
    public GobalException() {
        super();
    }
    public GobalException(CodeMsg codeMsg){

        super(codeMsg.getMsg());
        this.codeMsg=codeMsg;
    }

}
