package cn.com.pzg.sharding.jdbc.demo.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Copyright 2023 Shanghai Yejia Digital Technology Co.,Ltd. All rights reserved.
 *
 * @author pzg
 * @description
 * @since 2023/12/13
 */
@Data
public class OrderInfo {

    private String orderId;
    private String orderName;
    private Date createTime;
    private BigDecimal amount;
    private Long userId;
    private String code;
    private String projectName;

}
