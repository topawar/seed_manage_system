package com.topawar.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.topawar.manage.domain.Router;
import org.apache.ibatis.annotations.Insert;

/**
* @author 34424
* @description 针对表【ruoter】的数据库操作Mapper
* @createDate 2023-04-21 14:07:35
* @Entity generator.domain.Ruoter
*/
public interface RouterMapper extends BaseMapper<Router>{

    @Insert("insert into router(path,meta) values(#{path},#{meta}) on duplicate key update path=#{path}")
    void insertNameOnDuplicate(Router router);
}




