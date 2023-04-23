package com.topawar.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.topawar.manage.domain.Router;

/**
* @author 34424
* @description 针对表【ruoter】的数据库操作Service
* @createDate 2023-04-21 14:01:23
*/
public interface RouterService extends IService<Router> {
    void syncRoute(Router router);
}
