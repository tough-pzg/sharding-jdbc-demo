package cn.com.pzg.sharding.jdbc.demo;

import cn.com.pzg.sharding.jdbc.demo.bean.OrderInfo;
import cn.com.pzg.sharding.jdbc.demo.bean.Person;
import cn.com.pzg.sharding.jdbc.demo.bean.User;
import cn.com.pzg.sharding.jdbc.demo.mapper.OrderInfoMapper;
import cn.com.pzg.sharding.jdbc.demo.mapper.PersonMapper;
import cn.com.pzg.sharding.jdbc.demo.mapper.UserMapper;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class ShardingJdbcDemoApplicationTests {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PersonMapper personMapper;

    @Test
    public void test(){
        // 先使用不分表的数据源执行批量sql
        testBatchSql();

        // 测试使用 分表数据源新增
        testInsertOrder();
        System.out.println("使用sharding-jdbc 循环新增逻辑表");

        // 测试使用 分表数据源查询
        testGetOrder();
        System.out.println("使用sharding-jdbc 查询逻辑表");

    }

    @Test
    public void testBatchSql(){
        List<String> sexList = Arrays.asList("男", "女");
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Person temp = new Person();
            temp.setAge(20);
            temp.setName(UUID.randomUUID().toString());
            temp.setSex(sexList.get(i % 2));
            temp.setDeleted(0);
            list.add(temp);
        }

        personMapper.insertBatch(list);
        System.out.println("----------使用默认数据源批量插入成功----------");
    }

    @Test
    public void testGetUser() {
        User byNameId = userMapper.getById(5L);
        System.out.println("根据userId:" + JSON.toJSONString(byNameId));

        List<User> byNameLike = userMapper.getByNameLike("张");
        System.out.println("根据姓名模糊查询:" + JSON.toJSONString(byNameLike));

        System.out.println("----------------------");
    }

    @Test
    public void testInsertUser() {
        for (long i = 0; i < 9; i++) {
            User user = new User();
            user.setUserId(i);
            user.setUserName("张三");
            user.setAge(18);
            user.setEamil("123@qq.com");
            user.setAddress("上海市普陀区");
            userMapper.insert(user);
        }

        System.out.println("----------------------");
    }

    @Test
    public void testInsertUserBatch() {
        User user = new User();
        user.setUserId(10086L);
        user.setUserName("张三");
        user.setAge(18);
        user.setEamil("123@qq.com");
        user.setAddress("上海市普陀区");

        User user2 = new User();
        user2.setUserId(100867L);
        user2.setUserName("张三");
        user2.setAge(18);
        user2.setEamil("123@qq.com");
        user2.setAddress("上海市普陀区");

        userMapper.insertBatch(Arrays.asList(user,user2));

        System.out.println("----------------------");
    }


    @Test
    public void testGetOrder() {
        List<OrderInfo> all = orderInfoMapper.getAll();
        System.out.println("所有数据：" + JSON.toJSONString(all));

        List<OrderInfo> hfw = orderInfoMapper.getByProjectName("hfw");
        System.out.println("所有hfw：" + JSON.toJSONString(hfw));

        OrderInfo byOrderId = orderInfoMapper.getByOrderId("941659721893412864");
        System.out.println("byOrderId：" + JSON.toJSONString(byOrderId));


    }

    @Test
    public void testInsertOrder() {
        List<String> projectNameList = Arrays.asList("hfw", "why");
        for (int i = 0; i < 4; i++) {
            OrderInfo orderInfo = new OrderInfo();
//            orderInfo.setOrderId("123456" + i);
            orderInfo.setOrderName("测试");
            orderInfo.setCode("order20231213");
            orderInfo.setAmount(new BigDecimal("100.2935"));
            orderInfo.setProjectName(projectNameList.get(i % 2));
            orderInfo.setUserId(10086L);
            orderInfoMapper.insert(orderInfo);
        }
    }

}
