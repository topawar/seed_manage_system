package com.topawar.manage.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 34424
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 随机生成的uuid
     */
    @TableId
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
     * 头像地址
     */
    private String imageurl;

    /**
     * 修改时日期
     */
    private Integer role;

    /**
     * 注册时日期
     */
    private Date create_time;

    /**
     * 修改时日期
     */
    private Date update_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public String getCreate_time() {
        String format_Create_time = DateFormatUtils.format(create_time, "YYYY/MM/dd HH:mm:ss");
        return format_Create_time;
    }

}