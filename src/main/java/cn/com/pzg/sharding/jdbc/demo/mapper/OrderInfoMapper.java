package cn.com.pzg.sharding.jdbc.demo.mapper;

import cn.com.pzg.sharding.jdbc.demo.bean.OrderInfo;
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
 * @since 2023/12/13
 */
@Mapper
public interface OrderInfoMapper {


    @Insert(" INSERT INTO order_info (order_name,create_time,amount,user_id,code,project_name) " +
            " VALUES(#{orderInfo.orderName}," +
            "        #{orderInfo.createTime}," +
            "        #{orderInfo.amount}," +
            "        #{orderInfo.userId}," +
            "        #{orderInfo.code}," +
            "        #{orderInfo.projectName})")
    Integer insert(@Param("orderInfo") OrderInfo orderInfo);

    @Select("SELECT * FROM order_info")
    List<OrderInfo> getAll();

    @Select("SELECT * FROM order_info WHERE project_name = #{projectName} ")
    List<OrderInfo> getByProjectName(@Param("projectName") String projectName);

    @Select("SELECT * FROM order_info WHERE order_id = #{orderId} ")
    OrderInfo getByOrderId(@Param("orderId") String orderId);

}
