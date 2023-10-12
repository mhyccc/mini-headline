package com.minidy.my_news.service;

import com.minidy.my_news.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minidy.my_news.utils.Result;

/**
* @author 13798
* @description 针对表【news_user】的数据库操作Service
* @createDate 2023-10-11 19:50:30
*/
public interface UserService extends IService<User> {

    /**
     * 登录业务
     * @param user 用户账号密码
     * @return 结果类
     */
    Result login(User user);

    /**
     * 根据token获取用户数据
     * @param token token
     * @return 结果类
     */
    Result getUserInfo(String token);

    /**
     * 检查用户是否已经存在
     */
    Result checkUserName(String username);

    /*
        注册
     */
    Result register(User user);
}
