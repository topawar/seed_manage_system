package com.topawar.manage.common;

/**
 * @author: topawar
 * @date: 2022/12/14 14:09
 */
public enum ResponseCode {


    /**
     * 错误码
     * 错误信息
     * 描述
     */
    SUCCESS(20000,"ok",""),
    ERROR(50000,"系统异常",""),
    ERROR_PARAM_NULL(50001, "参数为空", ""),
    ERROR_USER_DOES_NOT_EXIST(50002, "用户不存在", "");


    private final int code;

    private final String msg;

    private final String desc;

    ResponseCode(int code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDesc() {
        return desc;
    }
}
