package com.topawar.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topawar.manage.common.BaseResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.topawar.manage.domain.User;
import com.topawar.manage.domain.request.LoginParam;
import com.topawar.manage.domain.request.PageParam;
import com.topawar.manage.domain.request.SearchUserParam;
import com.topawar.manage.domain.request.UpdateUserParam;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author 34424
 * @description 针对表【user】的数据库操作Service
 * @createDate 2022-12-14 10:02:39
 */
public interface UserService extends IService<User> {

    /**
     * @param loginParam
     * @return 返回用户信息
     */
    User login(LoginParam loginParam, HttpServletRequest request);

    Page<User> getUserListPage(PageParam pageParam);

    List<User> searchUser(SearchUserParam searchUserParam);

    Integer deleteUserById(String id);

    Integer updateUserById(UpdateUserParam updateUserParam);
}
