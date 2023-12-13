package cn.com.pzg.sharding.jdbc.demo;

import cn.com.pzg.sharding.jdbc.demo.bean.OrderInfo;
import cn.com.pzg.sharding.jdbc.demo.mapper.OrderInfoMapper;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ShardingJdbcDemoApplicationTests {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Test
    public void testGet(){
        List<OrderInfo> all = orderInfoMapper.getAll();
        System.out.println("所有数据：" + JSON.toJSONString(all));

        List<OrderInfo> hfw = orderInfoMapper.getByProjectName("hfw");
        System.out.println("所有hfw：" + JSON.toJSONString(hfw));

        OrderInfo byOrderId = orderInfoMapper.getByOrderId("941659721893412864");
        System.out.println("byOrderId：" + JSON.toJSONString(byOrderId));


    }

    @Test
    void contextLoads() {
        List<String> projectNameList = Arrays.asList("hfw", "why");
        for (int i = 0; i < 10; i++) {
            OrderInfo orderInfo = new OrderInfo();
//            orderInfo.setOrderId("123456" + i);
            orderInfo.setOrderName("测试");
            orderInfo.setCode("order20231213");
            orderInfo.setAmount(new BigDecimal("100.2935"));
            orderInfo.setProjectName(projectNameList.get(i % 2));
            orderInfo.setUserId(10086L);
            orderInfoMapper.insert(orderInfo);
            System.out.println("本次生成的ID:" + orderInfo.getOrderId());
        }
    }

}
