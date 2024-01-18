package com.ispan.mingle.projmingle.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ispan.mingle.projmingle.Service.OrderService;
import com.ispan.mingle.projmingle.domain.WorkBean;


@SpringBootTest
public class OrderServiceTests {
    
    @Autowired
    private OrderService orderService;

    @Test
    public void testselectWorkDetail() {
        WorkBean work =  orderService.selectWorkDetail(2);
        System.err.println(work);
    }

}
