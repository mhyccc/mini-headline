package com.minidy.my_news.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minidy.my_news.dto.PortalDto;
import com.minidy.my_news.pojo.Headline;
import com.minidy.my_news.service.HeadlineService;
import com.minidy.my_news.mapper.HeadlineMapper;
import com.minidy.my_news.utils.JwtHelper;
import com.minidy.my_news.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @author 13798
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2023-10-11 19:50:30
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Resource
    private HeadlineMapper headlineMapper;

    @Resource
    private JwtHelper jwtHelper;

    /*
        获取首页新闻(分页)
     */
    @Override
    public Result findNewsPage(PortalDto portalDto) {
        IPage<Map> page = new Page<>(portalDto.getPageNum(), portalDto.getPageSize());
        headlineMapper.selectMyPage(page, portalDto);

        Map pageInfo = new HashMap();
        pageInfo.put("pageData", page.getRecords());
        pageInfo.put("pageNum", page.getCurrent());
        pageInfo.put("pageSize", page.getSize());
        pageInfo.put("totalPage", page.getPages());
        pageInfo.put("totalSize", page.getTotal());

        Map data = new HashMap();
        data.put("pageInfo", pageInfo);


        return Result.ok(data);
    }

    /*
        获取新闻详情
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map headLineDetail = headlineMapper.selectDetailMap(hid);

        // 新闻浏览量 + 1
        Headline headline = new Headline();
        headline.setHid((Integer) headLineDetail.get("hid"));
        headline.setVersion((Integer) headLineDetail.get("version"));
        headline.setPageViews((Integer) headLineDetail.get("pageViews") + 1);

        headlineMapper.updateById(headline);

        Map data = new HashMap();
        data.put("headline", headLineDetail);

        return Result.ok(data);
    }


    /*
        发布新闻
     */
    @Override
    public Result publish(Headline headline, String token) {
        // 根据token 获取userId
        Long userId = jwtHelper.getUserId(token);

        // 设置 发布 userId
        headline.setPublisher(userId.intValue());
        // 设置创建时间 更新时间
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        // 设置访问量
        headline.setPageViews(0);

        headlineMapper.insert(headline);

        return Result.ok(null);

    }

    /*
        修改新闻
     */
    @Override
    public Result updateHeadline(Headline headline) {
        // 获取数据库该新闻的 version
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        // 设置version
        headline.setVersion(version);
        // 设置更新时间为现在
        headline.setUpdateTime(new Date());
        // 更新
        headlineMapper.updateById(headline);

        return Result.ok(null);
    }
}




