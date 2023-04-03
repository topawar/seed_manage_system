package com.topawar.manage.exception;

/**
 * @author: topawar
 * @date: 2022/12/14 10:10
 */
public class GlobalException extends RuntimeException {
    /**
     * 状态码
     */
    private final int code;

    /**
     * 详细描述
     */
    private final String desc;

    public GlobalException(String message) {
        super(message);
        this.code = 40000;
        this.desc = "";
    }

    public GlobalException(String message, int code) {
        super(message);
        this.code = code;
        this.desc = "";
    }

    public GlobalException(String message, int code, String desc) {
        super(message);
        this.code = code;
        this.desc = desc;
    }


    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
