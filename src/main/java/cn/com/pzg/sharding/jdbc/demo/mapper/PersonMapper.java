package cn.com.pzg.sharding.jdbc.demo.mapper;

import cn.com.pzg.sharding.jdbc.demo.bean.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Copyright 2023 Shanghai Yejia Digital Technology Co.,Ltd. All rights reserved.
 *
 * @author pzg
 * @description
 * @since 2023/9/22com.pzg.spring.web.test.mapper
 */
@Mapper
public interface PersonMapper {

    void insertBatch(@Param("rows") List<Person> rows);

    @Select("select * from person where id = #{id}")
    Person getById(@Param("id") Long id);

}
