package com.topawar.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.topawar.manage.domain.Router;
import com.topawar.manage.mapper.RouterMapper;
import com.topawar.manage.service.RouterService;
import jakarta.annotation.Resource;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.stereotype.Service;

/**
 * @author 34424
 * @description 针对表【ruoter】的数据库操作Service实现
 * @createDate 2023-04-21 14:01:23
 */
@Service
public class RouterServiceImpl extends ServiceImpl<RouterMapper, Router>
        implements RouterService {

    @Resource
    RouterMapper routerMapper;

    @Override
    public void syncRoute(@ParameterObject Router router) {
        routerMapper.insertNameOnDuplicate(router);
    }
}




