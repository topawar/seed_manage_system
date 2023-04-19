package com.topawar.manage.config;

import com.topawar.manage.common.ResponseCode;
import com.topawar.manage.common.util.RedisUtil;
import com.topawar.manage.exception.GlobalException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class authenticHandlerInterceptor implements HandlerInterceptor {

    @Resource
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("进入拦截器");
        String tokenId = request.getHeader("tokenId");
        if (StringUtils.isEmpty(tokenId)) {
            throw new GlobalException(ResponseCode.ERROR_NOT_LOGIN.getMsg(), ResponseCode.ERROR_NOT_LOGIN.getCode());
        }
        if (!redisUtil.hasKey(tokenId)){
            throw new GlobalException(ResponseCode.ERROR_TOKEN_INVALID.getMsg(), ResponseCode.ERROR_TOKEN_INVALID.getCode());
        }
        return true;
    }
}