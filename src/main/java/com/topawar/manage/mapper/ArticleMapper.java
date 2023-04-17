package com.topawar.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.topawar.manage.annotation.Page;
import com.topawar.manage.domain.Article;
import com.topawar.manage.domain.request.PageParam;

import java.util.List;

/**
* @author 34424
* @description 针对表【article】的数据库操作Mapper
* @createDate 2023-03-27 23:25:16
* @Entity generator.domain.article
*/
public interface ArticleMapper extends BaseMapper<Article> {
    @Page
    List<Article> selectListPage(PageParam pageParam);
}




