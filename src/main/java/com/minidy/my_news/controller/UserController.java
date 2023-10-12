package com.minidy.my_news.controller;

import com.minidy.my_news.pojo.User;
import com.minidy.my_news.service.UserService;
import com.minidy.my_news.utils.JwtHelper;
import com.minidy.my_news.utils.Result;
import com.minidy.my_news.utils.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * author: minidy
 * date: 2023/10/11
 * time: 22:25
 */

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private JwtHelper jwtHelper;

    /*
        登录
     */
    @PostMapping("login")
    public Result login(@RequestBody User user) {
        return userService.login(user);
    }

    /*
        获取用户信息
     */
    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token) {
        return userService.getUserInfo(token);
    }

    /*
        注册时检查用户名是否已经被使用
     */
    @PostMapping("checkUserName")
    public Result checkUserName(String username) {
        return userService.checkUserName(username);
    }


    /*
        注册
     */
    @PostMapping("register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }

    /*
        检查用户 登录状态(token是否过期)
     */
    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token) {
        boolean expiration = jwtHelper.isExpiration(token);

        if (expiration) {
            // token过期
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        return Result.ok(null);
    }

}
