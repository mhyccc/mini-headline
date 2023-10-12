package com.minidy.my_news.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minidy.my_news.dto.PortalDto;
import com.minidy.my_news.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author 13798
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2023-10-11 19:50:30
* @Entity com.minidy.my_news.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {
    /*
        自定义分页方法
     */
    IPage<Map> selectMyPage(IPage<Map> page, @Param("portalDto") PortalDto portalDto);

    Map selectDetailMap(Integer hid);
}




