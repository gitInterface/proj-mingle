// package com.ispan.mingle.projmingle.repository;

// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import java.util.List;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.jdbc.Sql;

// import com.ispan.mingle.projmingle.dto.UserOrderDTO;

// @DataJpaTest
// public class OrderRepositoryTest {

// @Autowired
// private OrderRepository orderRepository;

// @Test
// @Sql("/data.sql") // 加载测试数据
// public void testfindWorkDetailAndPhotoByUserid() {
// String userid = "12"; // 设置测试用户ID

// List<UserOrderDTO> orders =
// orderRepository.findWorkDetailAndPhotoByUserid(userid);

// assertNotNull(orders); // 确保结果不为空

// // 进行断言以验证查询结果是否符合预期
// // assertEquals(expectedSize, orders.size());
// // assertEquals(expectedValue, orders.get(0).getXXX());
// }
// }
