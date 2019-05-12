package cn.wolfcode.shop.exception;

import cn.wolfcode.shop.result.CodeMsg;
import cn.wolfcode.shop.result.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler({BindException.class,GobalException.class,RuntimeException.class})
    @ResponseBody
    public Result<CodeMsg> handleException(Exception e){
        if(e instanceof BindException){
            BindException bindException = (BindException) e;
            String defaultMessage = bindException.getAllErrors().get(0).getDefaultMessage();
            return Result.error(CodeMsg.PARAM_ERROR.fillArgs(defaultMessage));
        }else if(e instanceof GobalException||e instanceof RuntimeException){
            GobalException e1 = (GobalException) e;
//            密码不正确
            return  Result.error(e1.getCodeMsg());
        }
            e.printStackTrace();
            return Result.error(CodeMsg.SYSTEM_EXCEPTION);
    }
}
