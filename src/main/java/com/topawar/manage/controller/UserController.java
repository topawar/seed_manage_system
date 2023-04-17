package com.topawar.manage.controller;

import com.topawar.manage.common.BaseResponse;
import com.topawar.manage.common.util.CosUtil;
import com.topawar.manage.common.util.ResultUtil;
import com.topawar.manage.domain.request.LoginParam;
import com.topawar.manage.domain.request.PageParam;
import com.topawar.manage.domain.request.SearchUserParam;
import com.topawar.manage.domain.request.UpdateUserParam;
import com.topawar.manage.exception.GlobalException;
import com.topawar.manage.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
    private UserService userService;

    @Resource CosUtil cosUtil;

    @PostMapping("/login")
    public BaseResponse login(LoginParam loginParam) {
        if (StringUtils.isAnyBlank(loginParam.getName(), loginParam.getPassword())) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return userService.login(loginParam);
    }

    @PostMapping("/getUserList")
    public BaseResponse getUserList(PageParam pageParam) {
        if (pageParam.getPageNum() == 0 && pageParam.getPageSize() == 0) {
            pageParam.setPageNum(1);
            pageParam.setPageSize(5);
            return userService.getUserList(pageParam);
        }
        return userService.getUserList(pageParam);
    }

    @PostMapping("/searchUser")
    public BaseResponse searchUser(SearchUserParam searchUserParam) {
        if (StringUtils.isAnyBlank(searchUserParam.getName())) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return userService.searchUser(searchUserParam);
    }

    @PostMapping("/deleteUserById")
    public BaseResponse deleteUserById(String id) {
        if (StringUtils.isAnyBlank(id)) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return userService.deleteUserById(id);
    }

    @PostMapping("/updateUserById")
    public BaseResponse updateUserById(UpdateUserParam updateUserParam) {
        if (StringUtils.isAnyBlank(updateUserParam.getId())) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return userService.updateUserById(updateUserParam);
    }

    @PostMapping("/uploadAvatar")
    public BaseResponse uploadAvatar(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile == null){
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return ResultUtil.ok(cosUtil.putCosObjectFile(multipartFile));
    }
}
