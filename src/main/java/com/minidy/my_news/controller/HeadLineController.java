package com.minidy.my_news.controller;

import com.minidy.my_news.pojo.Headline;
import com.minidy.my_news.service.HeadlineService;
import com.minidy.my_news.utils.JwtHelper;
import com.minidy.my_news.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * author: minidy
 * date: 2023/10/12
 * time: 20:03
 */

@RestController
@RequestMapping("headline")
public class HeadLineController {
    @Resource
    private HeadlineService headlineService;

    /*
        发布新闻
     */
    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline, @RequestHeader String token) {
        return headlineService.publish(headline, token);
    }

    /*
        回显
        根据 新闻id查新闻数据
     */
    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(String hid) {
        // 直接使用 Mybatis-Plus提供的 service层的增强方法
        Headline headline = headlineService.getById(hid);

        // 封装结果 返回
        Map data = new HashMap();
        data.put("headline", headline);

        return Result.ok(data);
    }

    /*
        修改头条信息
     */
    @PostMapping("update")
    public Result updateHeadline(@RequestBody Headline headline) {
        return headlineService.updateHeadline(headline);
    }

    /*
        删除头条
     */

    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid) {
        // 直接使用 业务层进行删除 (与mapper层一样 也是逻辑删除 is_deleted=1)
        headlineService.removeById(hid);

        return Result.ok(null);
    }
}
