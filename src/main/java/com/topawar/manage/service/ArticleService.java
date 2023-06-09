package com.topawar.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topawar.manage.common.BaseResponse;
import com.topawar.manage.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.topawar.manage.domain.request.PageParam;

import java.util.List;
import java.util.Map;

/**
* @author 34424
* @description 针对表【article】的数据库操作Service
* @createDate 2023-03-27 23:25:16
*/
public interface ArticleService extends IService<Article> {
    Page<Article> getArticleListPage(PageParam pageParam);

    int deleteArticleById(int article_id);
}
