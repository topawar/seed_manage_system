package com.topawar.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topawar.manage.common.BaseResponse;
import com.topawar.manage.common.constant.CommonState;
import com.topawar.manage.common.util.CosUtil;
import com.topawar.manage.common.util.RedisUtil;
import com.topawar.manage.common.util.ResultUtil;
import com.topawar.manage.domain.User;
import com.topawar.manage.domain.request.LoginParam;
import com.topawar.manage.domain.request.PageParam;
import com.topawar.manage.domain.request.SearchUserParam;
import com.topawar.manage.domain.request.UpdateUserParam;
import com.topawar.manage.exception.GlobalException;
import com.topawar.manage.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    CosUtil cosUtil;

    @Resource
    RedisUtil redisUtil;

    @PostMapping("/login")
    public BaseResponse<User> login(@ParameterObject LoginParam loginParam, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(CommonState.LOGIN_USER);
        if (currentUser != null){
            return ResultUtil.ok(currentUser);
        }
        if (StringUtils.isAnyBlank(loginParam.getName(), loginParam.getPassword())) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return ResultUtil.ok(userService.login(loginParam,request));
    }

    @PostMapping("/getUserList")
    public BaseResponse<Page<User>> getUserList(@ParameterObject PageParam pageParam) {
        return ResultUtil.ok(userService.getUserListPage(pageParam));
    }

    @PostMapping("/searchUser")
    public BaseResponse<List<User>> searchUser(@ParameterObject SearchUserParam searchUserParam) {
        if (StringUtils.isAnyBlank(searchUserParam.getName())) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return ResultUtil.ok(userService.searchUser(searchUserParam));
    }

    @PostMapping("/deleteUserById")
    public BaseResponse<Integer> deleteUserById(String id) {
        if (StringUtils.isAnyBlank(id)) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return ResultUtil.ok(userService.deleteUserById(id));
    }

    @PostMapping("/updateUserById")
    public BaseResponse<Integer> updateUserById(@ParameterObject UpdateUserParam updateUserParam) {
        if (StringUtils.isAnyBlank(updateUserParam.getId())) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return ResultUtil.ok(userService.updateUserById(updateUserParam));
    }

    @PostMapping("/uploadAvatar")
    public BaseResponse<String> uploadAvatar(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        return ResultUtil.ok(cosUtil.putCosObjectFile(multipartFile));
    }
}
