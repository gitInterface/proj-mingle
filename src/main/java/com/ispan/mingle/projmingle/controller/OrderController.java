package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.OrderService;
import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.OrderWorkHouseBean;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.domain.WorkBean;





@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @GetMapping("/{userid}")
    public VolunteerDetailBean selectVolunteerDetail(@PathVariable String userid) {
        return orderService.selectVolunteerDetail(userid);
    }
    
    @PostMapping("/{workid}")
    public WorkBean selectWorkDetail(@PathVariable Integer workid) {
        return orderService.selectWorkDetail(workid);
    }
    
    /** 用photoid找照片 */
    @PostMapping("/photo/{photoid}")
    public List<String> selectHouseImages(@PathVariable Integer photoid) {
        List<String> list  =  orderService.selectHouseImages(photoid);
        return list;
    }

      /** 用workid找房子 */
    @GetMapping("/house/{workid}")
    public List<HouseBean> selectHouseDetail(@PathVariable Integer workid) {
         List<HouseBean> list = orderService.selectWorkHouse(workid);
    
        return list;
    }
    
    /** 創建訂單 */
    @PostMapping("/create")
    public OrderBean createOrder(@RequestBody OrderBean bean) {
        OrderBean order  = orderService.createOrder(bean);
        if(order != null){
            return order;
        }
        return null;
    } 

    /** 創建訂單與工作房子關係 */
    @PostMapping("/create/workhouse")
    public OrderWorkHouseBean createOrderWorkHouse(@RequestBody OrderWorkHouseBean bean) {
        OrderWorkHouseBean order  = orderService.createOrderWorkHouse(bean);
        if(order != null){
            return order;
        }
        return null;
    }

}
