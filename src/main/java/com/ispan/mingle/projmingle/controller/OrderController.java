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
import com.ispan.mingle.projmingle.domain.AccommodatorBean;
import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.OrderWorkHouseBean;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.domain.WorkHouseBean;
import org.springframework.web.bind.annotation.RequestParam;






@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;
    

    /** 用userid找會員詳細資料 */
    @GetMapping("/{userid}")
    public VolunteerDetailBean selectVolunteerDetail(@PathVariable String userid) {
        return orderService.selectVolunteerDetail(userid);
    }
    
    /** 用workid找工作 */
    @PostMapping("/{workid}")
    public WorkBean selectWorkDetail(@PathVariable Integer workid) {
        return orderService.selectWorkDetail(workid);
    }
    
    /** 用houseid找照片 */
    @PostMapping("/photo/{houseid}")
    public List<String> selectHouseImages(@PathVariable Integer houseid) {
        List<String> list  =  orderService.selectHouseImages(houseid);
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
    @PostMapping("/create/orderworkhouse")
    public OrderWorkHouseBean createOrderWorkHouse(@RequestBody OrderWorkHouseBean bean) {
        OrderWorkHouseBean order  = orderService.createOrderWorkHouse(bean);
        if(order != null){
            return order;
        }
        return null;
    }

    /** 用houseid找關聯工作房間的詳細資料 */
    @GetMapping("/workhouse/{houseid}")
    public List<WorkHouseBean> selectWorkHouseDetail(@PathVariable Integer houseid) {
        List<WorkHouseBean> list  =  orderService.selectWorkHouseDetail(houseid);
        return list;
    }

    /** 創建住宿者資料 */
    @PostMapping("/create/accommodator")
    public AccommodatorBean createAccommodator(@RequestBody AccommodatorBean bean) {
        AccommodatorBean order  = orderService.createAccommodator(bean);
        return order;
    }
    


}
