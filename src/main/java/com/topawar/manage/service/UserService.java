package com.topawar.manage.service;

import com.topawar.manage.common.BaseResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.topawar.manage.domain.User;
import com.topawar.manage.domain.request.LoginParam;
import com.topawar.manage.domain.request.SearchUserParam;

import java.util.List;

/**
* @author 34424
* @description 针对表【user】的数据库操作Service
* @createDate 2022-12-14 10:02:39
*/
public interface UserService extends IService<User> {

    /**
     *
     * @param loginParam
     * @return 返回用户信息
     */
    BaseResponse<User> login(LoginParam loginParam);

    BaseResponse<List<User>> getUserList();

    BaseResponse<List<User>> searchUser(SearchUserParam searchUserParam);
}
