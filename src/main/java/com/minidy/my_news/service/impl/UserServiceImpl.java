package com.minidy.my_news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minidy.my_news.pojo.User;
import com.minidy.my_news.service.UserService;
import com.minidy.my_news.mapper.UserMapper;
import com.minidy.my_news.utils.JwtHelper;
import com.minidy.my_news.utils.MD5Util;
import com.minidy.my_news.utils.Result;
import com.minidy.my_news.utils.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 13798
 * @description 针对表【news_user】的数据库操作Service实现
 * @createDate 2023-10-11 19:50:30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private JwtHelper jwtHelper;
    /**
     * 登录业务
     *   1.根据账号，查询用户对象  - loginUser
     *   2.如果用户对象为null，查询失败，账号错误！ 501
     *   3.对比，密码 ，失败 返回503的错误
     *   4.根据用户id生成一个token, token -> result 返回
     */
    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());

        User loginUser = userMapper.selectOne(lambdaQueryWrapper);

        if (loginUser == null) {
            // 账号错误
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        if (MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())) {
            // 账号密码都正确 那么就根据id 生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            // 将token封装到result返回
            Map data = new HashMap();
            data.put("token", token);
            return Result.ok(data);
        }

        // 密码错误
        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }


    /**
     * 根据token获取用户数据
     *      1. token是否过期
     *      2. 如果不过期, JWT获取userId
     *      3. 根据userId查询用户
     *      4. userPwd置空 封装到结果类中
     */
    @Override
    public Result getUserInfo(String token) {
        boolean expiration = jwtHelper.isExpiration(token);

        if (expiration) {
            // token过期
            Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        // 不过期 获取userId
        int userId = jwtHelper.getUserId(token).intValue();
        User loginUser = userMapper.selectById(userId);
        loginUser.setUserPwd("");

        Map data = new HashMap();
        data.put("loginUser", loginUser);

        return Result.build(data, ResultCodeEnum.SUCCESS);
    }


    /**
     * 检查用户是否已经存在(注册)
     *      1. 根据 username 查询count
     *      2. count = 0 可以注册
     *      3. 否则 返回505
     */
    @Override
    public Result checkUserName(String username) {
        if (check(username) > 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }

        return Result.ok(null);
    }

    /*
        注册业务
          1. 根据check()函数 检查是否存在 用户名相同的
          2. 如果 == 0 那么加密 密码 -> 插入
          3. 如果 > 0 就返回 505 userNameUsed
     */
    @Override
    public Result register(User user) {
        // 检查用户名是否存在
        if (check(user.getUsername()) > 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }

        // 不存在 可以注册
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        // Field 'nick_name' doesn't have a default value
        userMapper.insert(user);

        return Result.ok(null);
    }

    /*
        公共函数
        检查用户名是否已经存在 返回存在的用户数
     */
    private Long check(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        return userMapper.selectCount(lambdaQueryWrapper);
    }
}




