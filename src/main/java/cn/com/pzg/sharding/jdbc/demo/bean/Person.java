package cn.com.pzg.sharding.jdbc.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright 2023 Shanghai Yejia Digital Technology Co.,Ltd. All rights reserved.
 *
 * @author pzg
 * @description
 * @since 2023/9/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Long id;

    private Integer age;

    private String name;

    private String sex;

    private String createTime;

    private Integer deleted;

    public Person(Integer age, String name, String sex, String createTime, Integer deleted) {
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.createTime = createTime;
        this.deleted = deleted;
    }
}
