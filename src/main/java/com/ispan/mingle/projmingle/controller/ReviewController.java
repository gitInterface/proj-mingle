package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.OrderService;
import com.ispan.mingle.projmingle.Service.ReviewService;
import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.ReviewBean;
import com.ispan.mingle.projmingle.dto.ReviewDTO;


@RestController
@RequestMapping("/review")
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private OrderService orderService;

    /** 用房東id找出訂單 */
    @GetMapping("/findorder")
    public List<OrderBean> getOrderByLandlordId(@RequestParam Integer landlordId) {
        List<OrderBean> order = reviewService.getOrderByLandlordId(landlordId);
        return order;
    }

    /** 用房東id找出評論*/
    @GetMapping("/findreview")
    public  List<ReviewBean> getReviewByLandlordId(@RequestParam Integer landlordId) {
        List<ReviewBean> reviewBeans  = reviewService.getReviewByLandlordId(landlordId);
        return reviewBeans;
    }

    @GetMapping("/findLandlord")
    public LandlordBean getLandlord(@RequestParam String userid) {
        return  reviewService.getLandlordByUserid(userid);
    }

    @GetMapping("/findOrderDetail")
    public ReviewDTO getOrderDetail(@RequestParam Integer orderid) {
        return orderService.findReviewDTOByOrderId(orderid);
    }
    
    


}
