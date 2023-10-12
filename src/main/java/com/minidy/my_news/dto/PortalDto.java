package com.minidy.my_news.dto;

import lombok.Data;

/**
 * author: minidy
 * date: 2023/10/12
 * time: 15:44
 */
@Data
public class PortalDto {
    private String keyWords;
    private int type = 0;
    private int pageNum = 1;
    private int pageSize = 10;
}
