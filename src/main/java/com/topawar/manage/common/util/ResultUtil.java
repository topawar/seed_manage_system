package com.topawar.manage.common.util;

import com.topawar.manage.common.BaseResponse;
import com.topawar.manage.common.ResponseCode;

import static com.topawar.manage.common.ResponseCode.SUCCESS;

/**
 * @author: topawar
 * @date: 2022/12/14 14:29
 */
public class ResultUtil {

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(SUCCESS.getCode(), data, SUCCESS.getMsg());
    }

    public static <T> BaseResponse<T> error(ResponseCode responceCode, String msg, String desc) {
        return new BaseResponse<>(responceCode.getCode(),msg,desc);
    }


    public static <T> BaseResponse<T> error(ResponseCode responceCode, String msg) {
        return new BaseResponse<>(responceCode.getCode(),msg);
    }

    public static <T> BaseResponse<T> error(int code, String msg) {
        return new BaseResponse<>(code,msg);
    }
}
