package com.topawar.manage.exception;

import com.topawar.manage.common.BaseResponse;
import com.topawar.manage.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.topawar.manage.common.ResponseCode.ERROR;


/**
 * @author: topawar
 * @date: 2022/12/14 13:58
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public BaseResponse globalExceptionHandler(GlobalException e){
        log.error(e.getMessage());
        return ResultUtil.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e){
        log.error(e.getMessage());
        return ResultUtil.error(ERROR.getCode(),e.getMessage());
    }
}
