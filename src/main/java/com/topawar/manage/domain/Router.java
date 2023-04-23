package com.topawar.manage.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName ruoter
 */
@TableName(value ="router")
@Data
public class Router implements Serializable {
    /**
     * 
     */
    private String path;

    /**
     * 
     */
    private String meta;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}