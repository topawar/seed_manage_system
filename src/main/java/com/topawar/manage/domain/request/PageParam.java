package com.topawar.manage.domain.request;

import lombok.Data;

/**
 * @author: topawar
 * @date: 2023/03/28 10:52
 */
@Data
public class PageParam {
    private int pageNum=1;

    private int pageSize=5;
}
