package com.minidy.my_news.controller;

import com.minidy.my_news.dto.PortalDto;
import com.minidy.my_news.pojo.Type;
import com.minidy.my_news.service.HeadlineService;
import com.minidy.my_news.service.TypeService;
import com.minidy.my_news.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: minidy
 * date: 2023/10/12
 * time: 15:20
 */

@RestController
@RequestMapping("portal")
public class PortalController {
    @Resource
    private TypeService typeService;


    @Resource
    private HeadlineService headlineService;
    /*
        获取新闻类别
     */
    @GetMapping("findAllTypes")
    public Result findAllTypes() {
        /*
            直接调用业务层,查询全部数据
            不用再去调mapper层了
            SELECT tid,tname,version,is_deleted FROM news_type WHERE is_deleted=0
            为什么 version is_deleted也会被查到?
            如果不想被查到 应该需要自己写SQL 或者使用 mapper层的SQL
         */
        //List<Type> types = typeService.list(null);
        //return Result.ok(types);
        return typeService.findAllTypes();
    }

    /*
        获取首页新闻(分页)
     */
    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalDto portalDto) {
        return headlineService.findNewsPage(portalDto);
    }

    /*
        获取头条详情
     */
    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid) {
        return headlineService.showHeadlineDetail(hid);
    }
}
