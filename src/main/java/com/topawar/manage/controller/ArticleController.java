package com.topawar.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topawar.manage.common.BaseResponse;
import com.topawar.manage.common.util.ResultUtil;
import com.topawar.manage.domain.Article;
import com.topawar.manage.domain.request.PageParam;
import com.topawar.manage.exception.GlobalException;
import com.topawar.manage.service.ArticleService;
import jakarta.annotation.Resource;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.topawar.manage.common.ResponseCode.ERROR_PARAM_NULL;

/**
 * @author: topawar
 * @date: 2023/03/27 23:28
 * @description 文章管理
 */
@RequestMapping("article")
@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping("/getArticleList")
    public BaseResponse<Page<Article>> getArticleList(@ParameterObject PageParam pageParam) {
        return ResultUtil.ok(articleService.getArticleListPage(pageParam));
    }

    @PostMapping("/deleteArticleById")
    public BaseResponse<Integer> deleteArticleById(int article_id) {
        if (article_id == 0) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }

        return ResultUtil.ok(articleService.deleteArticleById(article_id));
    }
}
