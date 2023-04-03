package com.topawar.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article implements Serializable {
    /**
     * 文章id
     */
    @TableId(type = IdType.AUTO)
    private Long article_id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 标题
     */
    private String title;

    /**
     * 目录
     */
    private String catalogue;

    /**
     * 内容
     */
    private String content;

    /**
     * 文章分类
     */
    private String article_kind;

    /**
     * 点击量
     */
    private int pointnum;

    /**
     * 喜爱数
     */
    private int favoritenum;

    /**
     * 封面地址
     */
    private String imageurl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}