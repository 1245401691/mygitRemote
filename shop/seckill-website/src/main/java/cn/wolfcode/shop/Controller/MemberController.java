package cn.wolfcode.shop.Controller;

import cn.wolfcode.shop.service.IUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class MemberController {

    @Reference
    private IUserService userService;

    @GetMapping("/{nickName}")
    public Object get(@PathVariable("nickName") String nickName ){
        System.out.println(userService);
        return userService.get(nickName);
    }

}
