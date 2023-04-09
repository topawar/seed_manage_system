package com.topawar.manage.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: topawar
 * @date: 2023/04/09 20:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserParam {
    private String id;

    /**
     * 用户id，从10000开始
     */
    private String uid;

    /**
     * 用户名，做限制，长度不能超过10
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 0表示女，1表示男
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 用户是否有效标识 0无效，1有效
     */
    private Integer effectiveTag;

    /**
     * 用户权限
     */
    private Integer role;


    /**
     * 修改时日期
     */
    private Date update_time;

}
