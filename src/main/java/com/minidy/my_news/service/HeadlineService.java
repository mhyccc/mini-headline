package com.minidy.my_news.service;

import com.minidy.my_news.dto.PortalDto;
import com.minidy.my_news.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minidy.my_news.utils.Result;

/**
* @author 13798
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2023-10-11 19:50:30
*/
public interface HeadlineService extends IService<Headline> {

    Result findNewsPage(PortalDto portalDto);

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline, String token);

    Result updateHeadline(Headline headline);
}
