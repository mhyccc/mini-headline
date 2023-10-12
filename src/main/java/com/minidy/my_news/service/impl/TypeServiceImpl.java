package com.minidy.my_news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minidy.my_news.dto.PortalDto;
import com.minidy.my_news.pojo.Type;
import com.minidy.my_news.service.TypeService;
import com.minidy.my_news.mapper.TypeMapper;
import com.minidy.my_news.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 13798
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2023-10-11 19:50:30
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Resource
    private TypeMapper typeMapper;

    @Override
    public Result findAllTypes() {
        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);
    }
}




