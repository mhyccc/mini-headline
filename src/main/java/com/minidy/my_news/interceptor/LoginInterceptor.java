package com.minidy.my_news.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minidy.my_news.utils.JwtHelper;
import com.minidy.my_news.utils.Result;
import com.minidy.my_news.utils.ResultCodeEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 * author: minidy
 * date: 2023/10/12
 * time: 19:51
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 header获取token
        String token = request.getHeader("token");

        boolean expiration = jwtHelper.isExpiration(token);

        /*
            if (!jwtHelper.isExpiration(token)) {}
            不能直接这么写 会导致 JWT解析异常
         */

        // 如果token有效 就放行
        if (!expiration) {
            return true;
        }

        /*
            否则 返回504的状态json
         */
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);

        return false;
    }
}
