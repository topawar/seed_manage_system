package com.topawar.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.topawar.manage.common.BaseResponse;
import com.topawar.manage.common.util.ResultUtil;
import com.topawar.manage.domain.Article;
import com.topawar.manage.domain.pojo.PageFilter;
import com.topawar.manage.domain.request.PageParam;
import com.topawar.manage.exception.GlobalException;
import com.topawar.manage.mapper.ArticleMapper;
import com.topawar.manage.service.ArticleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.topawar.manage.common.ResponseCode.ERROR;
import static com.topawar.manage.common.ResponseCode.ERROR_PARAM_NULL;

/**
 * @author 34424
 * @description 针对表【article】的数据库操作Service实现
 * @createDate 2023-03-27 23:25:16
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;


    @Override
    public BaseResponse<Map<String, Object>> getArticleList(PageParam pageParam) {
        Map<String, Object> resultMap = new HashMap<>();
        PageFilter pageFilter = pageParam.getPageFilter(pageParam,articleMapper.selectListPage(pageParam));
        resultMap.put("pageList", pageFilter.getData());
        resultMap.put("total", pageFilter.getPages());
        return ResultUtil.ok(resultMap);
    }

    @Override
    public BaseResponse deleteArticleById(int article_id) {

        if (article_id == 0) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }

        int i = articleMapper.deleteById(article_id);

        if (i != 1) {
            return ResultUtil.error(ERROR.getCode(),"删除失败");
        }

        return ResultUtil.ok(i);
    }

}




