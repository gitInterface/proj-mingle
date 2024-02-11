package com.ispan.mingle.projmingle.controller;

import java.util.List;

import com.ispan.mingle.projmingle.dto.LandlordOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.HouseService;
import com.ispan.mingle.projmingle.Service.OrderService;
import com.ispan.mingle.projmingle.Service.WorkService;
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

    @Autowired
    private HouseService houseService;

    @Autowired
    private WorkService workService;
    

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
    

    /** 用orderid找會員資料 */
    @GetMapping("/findUserDetail")
    public VolunteerDetailBean selectVolunteerDetailByUserId(@RequestParam("orderid") Integer orderid){
        return orderService.getVolunteerDetailByOrderId(orderid);

    }   

    /** 用orderid找訂單 */
    @GetMapping("/findOrder")
    public OrderBean selectOrderDetailbyOrderId(@RequestParam("orderid") Integer orderid) {
        return orderService.getOrderDetailByOrderId(orderid);
    }
    
    /** 用orderid查工作 */
    @GetMapping("/findWork")
    public WorkBean selectWorkDetailByOrderId(@RequestParam("orderid") Integer orderid) {
        return orderService.findWorkBeanByOrderId(orderid);
    }

    /** 用orderid查房子 */
    @GetMapping("/findHouse")
    public List<HouseBean> selectHouseDetailByOrderId(@RequestParam("orderid") Integer orderid) {
        return orderService.findHouseBeanByOrderId(orderid);
    }

    /** 修改房間床位 */
    @GetMapping("/updateBeds")
    public HouseBean modifyBeds(@RequestParam Integer houseid, @RequestParam Integer attendance) {
        return houseService.updateBeds(houseid, attendance);
        
    }

    /** 修改工作報名人數 */
    @GetMapping("/updateAttendee")
    public WorkBean modifyAttendees(@RequestParam Integer workid, @RequestParam Integer attendance) {
        return workService.updateAttendance(workid, attendance);

    }

    @GetMapping("/findAllOrder/{id}")
    List<LandlordOrderDTO> getAllOrder(@PathVariable Integer id){
        return orderService.getAllOrderByLoardId(id);
    }

    @PostMapping("/acceptOrder/{id}")
    List<LandlordOrderDTO> setOrderStatus(@PathVariable Integer id) {
        orderService.setOrderStatus(id, "房東已接受", false);
        return orderService.getOrderByLoardId(id);
    }

    @PostMapping("/rejectOrder/{id}")
    List<LandlordOrderDTO> setRejectOrder(@PathVariable Integer id) {
        orderService.setOrderStatus(id, "房東已拒絕", true);
        return orderService.getOrderByLoardId(id);
    }
}



