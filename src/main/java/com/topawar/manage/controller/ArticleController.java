package com.topawar.manage.controller;

import com.topawar.manage.common.BaseResponse;
import com.topawar.manage.domain.request.PageParam;
import com.topawar.manage.exception.GlobalException;
import com.topawar.manage.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public BaseResponse getArticleList(PageParam pageParam) {

        if (pageParam.getPageNum() == 0 && pageParam.getPageSize() == 0) {
            pageParam.setPageNum(1);
            pageParam.setPageSize(5);
            return articleService.getArticleList(pageParam);
        }

        return articleService.getArticleList(pageParam);
    }

    @PostMapping("/deleteArticleById")
    public BaseResponse deleteArticleById(int article_id) {
        if (article_id == 0) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }

        return articleService.deleteArticleById(article_id);
    }
}
