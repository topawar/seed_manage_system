package com.topawar.manage.config;

import com.topawar.manage.common.ResponseCode;
import com.topawar.manage.common.constant.CommonState;
import com.topawar.manage.common.util.RedisUtil;
import com.topawar.manage.domain.User;
import com.topawar.manage.exception.GlobalException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AuthenticHandlerInterceptor implements HandlerInterceptor {

    @Resource
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestURL = String.valueOf(request.getRequestURL());
        User currentUser = (User) request.getSession().getAttribute(CommonState.LOGIN_USER);
        if (!requestURL.contains("http://localhost:8082/doc")){
            if (currentUser == null) {
                throw new GlobalException(ResponseCode.ERROR_NOT_LOGIN.getMsg(), ResponseCode.ERROR_NOT_LOGIN.getCode());
            }
            log.info("token是"+ currentUser);
        }
        return true;
    }
}