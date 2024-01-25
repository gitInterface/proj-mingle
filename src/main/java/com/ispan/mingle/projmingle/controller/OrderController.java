package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.OrderService;
import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.domain.HousePhotoBean;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.domain.WorkBean;
import org.springframework.web.bind.annotation.RequestParam;




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
    
    @GetMapping("/photo/{photoid}")
    public List<HousePhotoBean> selectHouseImages(@PathVariable Integer photoid) {
        List<HousePhotoBean> list  =  orderService.selectHouseImages(photoid);
        return list;
    }

    @GetMapping("/house/{workid}")
    public List<HouseBean> getMethodName(@PathVariable Integer workid) {
         List<HouseBean> list = orderService.selectWorkHouse(workid);
    
        return list;
    }
    
    
    
}
