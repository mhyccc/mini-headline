package com.minidy.my_news.service;

import com.minidy.my_news.dto.PortalDto;
import com.minidy.my_news.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minidy.my_news.utils.Result;

/**
* @author 13798
* @description 针对表【news_type】的数据库操作Service
* @createDate 2023-10-11 19:50:30
*/
public interface TypeService extends IService<Type> {
    Result findAllTypes();
}
