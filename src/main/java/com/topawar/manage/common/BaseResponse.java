package com.topawar.manage.common;

import lombok.Data;

/**
 * @author: topawar
 * @date: 2022/12/14 14:25
 */
@Data
public class BaseResponse<T> {

    private final int code;

    private final T data;

    private final String msg;

    private final String desc;


    public BaseResponse(int code, String msg) {
        this.code = code;
        this.data = null;
        this.msg = msg;
        this.desc = "";
    }

    public BaseResponse(int code, String msg, String desc) {
        this.code = code;
        this.data = null;
        this.msg = msg;
        this.desc = desc;
    }
    public BaseResponse(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.desc = "";
    }

    public BaseResponse(int code, T data, String msg, String desc) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.desc = desc;
    }
}
