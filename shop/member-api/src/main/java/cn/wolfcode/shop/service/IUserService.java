package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.User;

public interface IUserService {
    User get(String mobile);

    String login(String mobile, String password);
}
