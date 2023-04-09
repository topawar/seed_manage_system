package com.topawar.manage.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topawar.manage.domain.pojo.PageFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

/**
 * @author: topawar
 * @date: 2023/04/03 15:04
 * 分页aop
 */
@Configuration
@Aspect
@Slf4j
//开启切面增强
@EnableAspectJAutoProxy
public class PageAop {

    /**
     * 定义切面点
     */
    @Pointcut("@annotation(com.topawar.manage.annotation.Page)")
    public void annotation() {

    }

    @Around("annotation()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        //当前页码
        int pageNum = 1;
        //每页记录数
        int pageSize = 5;
        PageFilter pageFilter = null;
        // 获取方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof PageFilter) {
                pageFilter = (PageFilter) arg;
                pageNum = ObjectUtils.isEmpty(pageFilter.getPageNum()) ? pageNum : pageFilter.getPageNum();
                pageSize = ObjectUtils.isEmpty(pageFilter.getPageSize()) ? pageSize : pageFilter.getPageSize();
            }
        }
        Object result = null;
        try {

            assert pageFilter != null;
            log.info("切点前"+pageFilter.toString());
            Page<Object> page = PageHelper.startPage(pageFilter.getPageNum(), pageFilter.getPageSize());
            result = proceedingJoinPoint.proceed(args);
            pageFilter.setPageNum(page.getPageNum());
            pageFilter.setPages(page.getPages());
            pageFilter.setPageSize(page.getPageSize());
            pageFilter.setTotal(page.getTotal());
        } catch (Exception e) {
            log.info("查询数据库异常" + e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
