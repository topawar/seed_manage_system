package com.topawar.manage.domain.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author: topawar
 * @date: 2023/04/03 15:18
 * 分页类
 */
@Data
public class PageFilter <T>{
    private long total;

    private int pageNum;

    private int pageSize;

    private int pages;

    private List<T> data;

    public PageFilter getPageFilter(PageFilter obj,List<T> response){
        PageFilter<T> pages = new PageFilter<>();
        pages.setPageNum(obj.getPageNum());
        pages.setPages(obj.getPages());
        pages.setPageSize(obj.getPageSize());
        pages.setTotal(obj.getTotal());
        pages.setData(response);
        return pages;
    }
}
