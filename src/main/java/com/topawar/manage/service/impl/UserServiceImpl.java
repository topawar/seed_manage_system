package com.topawar.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.topawar.manage.common.BaseResponse;
import com.topawar.manage.common.ResultUtil;
import com.topawar.manage.domain.User;
import com.topawar.manage.domain.pojo.PageFilter;
import com.topawar.manage.domain.request.LoginParam;
import com.topawar.manage.domain.request.PageParam;
import com.topawar.manage.domain.request.SearchUserParam;
import com.topawar.manage.exception.GlobalException;
import com.topawar.manage.mapper.UserMapper;
import com.topawar.manage.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.topawar.manage.common.ResponseCode.ERROR_PARAM_NULL;
import static com.topawar.manage.common.ResponseCode.ERROR_USER_DOES_NOT_EXIST;

/**
* @author 34424
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-12-14 10:02:39
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public BaseResponse<User> login(LoginParam loginParam) {

        if (StringUtils.isAnyBlank(loginParam.getName(),loginParam.getPassword())){
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(),ERROR_PARAM_NULL.getCode());
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("name",loginParam.getName());
        userQueryWrapper.eq("password",loginParam.getPassword());
        User user = userMapper.selectOne(userQueryWrapper);
        if (null == user){
            throw new GlobalException(ERROR_USER_DOES_NOT_EXIST.getMsg(), ERROR_USER_DOES_NOT_EXIST.getCode());
        }
        User safetyUser = safetyUser(user);
        return ResultUtil.ok(safetyUser);
    }

    @Override
    public BaseResponse<Map<String, Object>> getUserList(PageParam pageParam) {
        Map<String, Object> resultMap = new HashMap<>();
        PageFilter pageFilter = pageParam.getPageFilter(pageParam, userMapper.selectList(pageParam));
        List data = pageFilter.getData();
        if (null == data){
            throw new GlobalException(ERROR_USER_DOES_NOT_EXIST.getMsg(), ERROR_USER_DOES_NOT_EXIST.getCode());
        }

        resultMap.put("pageList", data);
        resultMap.put("total", pageFilter.getPages());
        return ResultUtil.ok(resultMap);
    }

    @Override
    public BaseResponse<List<User>> searchUser(SearchUserParam searchUserParam) {
        if (StringUtils.isAnyBlank(searchUserParam.getName())) {
            throw new GlobalException(ERROR_PARAM_NULL.getMsg(), ERROR_PARAM_NULL.getCode());
        }
        QueryWrapper<User> searchUserParamQueryWrapper = new QueryWrapper<>();
        searchUserParamQueryWrapper.like("name",searchUserParam.getName());
        List<User> userList = userMapper.selectList(searchUserParamQueryWrapper);
        return ResultUtil.ok(userList);
    }

    public User safetyUser(User user){
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setName(user.getName());
        safetyUser.setPassword(user.getPassword());
        safetyUser.setGender(user.getGender());
        safetyUser.setAge(user.getAge());
        safetyUser.setImageurl(user.getImageurl());
        safetyUser.setRole(user.getRole());
        return safetyUser;
    }
}




