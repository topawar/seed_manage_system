package com.topawar.manage.controller;

import com.topawar.manage.common.BaseResponse;
import com.topawar.manage.common.util.ResultUtil;
import com.topawar.manage.domain.Router;
import com.topawar.manage.service.RouterService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: YJ
 * @date: 2023/04/21 14:00
 * 用于操作vue路由
 */
@RestController
@RequestMapping("/route")
public class RouteController {
    @Resource
    RouterService routerService;

    @PostMapping("/syncRoute")
    public void syncRoute(Router router){
        routerService.syncRoute(router);
    }

    @GetMapping("/getRouteList")
    public BaseResponse getRouteList(){
        return ResultUtil.ok(routerService.list());
    }
}
