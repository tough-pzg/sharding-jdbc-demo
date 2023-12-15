package cn.com.pzg.sharding.jdbc.demo.bean;

import lombok.Data;

import java.util.Date;

/**
 * Copyright 2023 Shanghai Yejia Digital Technology Co.,Ltd. All rights reserved.
 *
 * @author pzg
 * @description
 * @since 2023/12/15
 */
@Data
public class User {

    private Long userId;
    private String userName;
    private Integer age;
    private String address;
    private String eamil;
    private Date createTime;

}
