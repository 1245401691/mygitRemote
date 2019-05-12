package cn.wolfcode.shop.vo;

import cn.wolfcode.shop.util.IsMobile;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 该类用来封装用户的用户名跟密码
 */
@Getter
@Setter
public class LoginVo {
//    @Pattern(regexp = "1\\d{10}",message = "手机格式不正确")
    @IsMobile
    private String mobile;
    @NotEmpty(message = "密码不能为空")
    private String password;
}
