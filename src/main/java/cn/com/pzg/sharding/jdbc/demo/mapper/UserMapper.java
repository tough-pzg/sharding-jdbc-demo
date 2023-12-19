package cn.com.pzg.sharding.jdbc.demo.mapper;

import cn.com.pzg.sharding.jdbc.demo.bean.User;
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
 * @since 2023/12/15
 */
@Mapper
public interface UserMapper {

    @Insert(" INSERT INTO user (user_id, user_name, age, address, eamil, create_time )" +
            " VALUES (" +
            " #{user.userId}," +
            " #{user.userName}," +
            " #{user.age}," +
            " #{user.address}," +
            " #{user.eamil}," +
            " #{user.createTime}" +
            " );")
    Integer insert(@Param("user") User user);

    @Insert(" <foreach collection=\"list\"  item=\"row\" separator=\";\">\n" +
            "    INSERT INTO user (user_id, user_name, age, address, eamil, create_time )\n" +
            "    VALUES (#{row.userId},#{row.userName},#{row.age}, #{row.address},#{row.eamil},#{row.createTime});\n" +
            " </foreach>")
    void insertBatch(@Param("list") List<User> list);

    @Select("SELECT * from user WHERE user_name like CONCAT('%',#{userName} ,'%')")
    List<User> getByNameLike(@Param("userName") String userName);

    @Select("SELECT * from user WHERE user_id = #{userId} ")
    User getById(@Param("userId") Long userId);

}
