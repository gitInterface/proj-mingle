package com.ispan.mingle.projmingle.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.OrderService;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.domain.WorkBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;





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
    
    
}
