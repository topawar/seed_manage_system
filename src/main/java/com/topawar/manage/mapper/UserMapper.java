package com.topawar.manage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.topawar.manage.annotation.Page;
import com.topawar.manage.domain.Article;
import com.topawar.manage.domain.User;
import com.topawar.manage.domain.request.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author topawar
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2022-12-14 10:02:39
 * @Entity generator.domain.User
 */
public interface UserMapper extends BaseMapper<User> {

    @Page
    List<Article> selectList(PageParam pageParam);
}




