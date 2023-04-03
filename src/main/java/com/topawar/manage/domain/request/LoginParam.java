package com.topawar.manage.domain.request;

import lombok.Data;

/**
 * @author: topawar
 * @date: 2022/12/14 20:54
 */
@Data
public class LoginParam {
    /**
     * 用户名，做限制，长度不能超过10
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;
}
