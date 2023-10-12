package com.minidy.my_news;

import com.minidy.my_news.utils.JwtHelper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyNewsApplicationTests {

    @Resource
    private JwtHelper jwtHelper;

    @Test
    public void test(){
        // 根据userId 生成 token
        String token = jwtHelper.createToken(1L);
        System.out.println("token = " + token);

        // 根据token 解析出 userId
        int userId = jwtHelper.getUserId(token).intValue();
        System.out.println("userId = " + userId);

        // 校验是否到期!  false表示token还没到期
        boolean expiration = jwtHelper.isExpiration(token);
        System.out.println("expiration = " + expiration);
    }

}
