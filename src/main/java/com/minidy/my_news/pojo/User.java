package com.minidy.my_news.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * @TableName news_user
 */
@Data
public class User implements Serializable {
    /*
        class com.minidy.my_news.pojo.User,Not found @TableId annotation,
        Cannot use Mybatis-Plus 'xxById' Method.
        原因就是没有加 @TableId
     */
    @TableId
    private Integer uid;

    private String username;

    private String userPwd;

    private String nickName;

    @Version
    private Integer version;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}