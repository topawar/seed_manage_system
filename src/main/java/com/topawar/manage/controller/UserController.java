package com.topawar.manage.controller;

import com.topawar.manage.common.BaseResponse;
import com.topawar.manage.domain.request.LoginParam;
import com.topawar.manage.domain.request.SearchUserParam;
import com.topawar.manage.exception.GlobalException;
import com.topawar.manage.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;


import static com.topawar.manage.common.ResponseCode.ERROR_PARAM_NULL;

/**
 * @author: topawar
 * @date: 2022/12/14 9:30.
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private  UserService userService;

    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginParam loginParam) {
        if (StringUtils.isAnyBlank(loginParam.getName(), loginParam.getPassword())) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return userService.login(loginParam);
    }

    @PostMapping("/getUserList")
    public BaseResponse getUserList(){

        return userService.getUserList();
    }

    @PostMapping("searchUser")
    public BaseResponse searchUser(@RequestBody SearchUserParam searchUserParam){
        if (StringUtils.isAnyBlank(searchUserParam.getName())) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return userService.searchUser(searchUserParam);
    }
}
