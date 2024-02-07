package com.ispan.mingle.projmingle.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.tags.shaded.org.apache.xpath.operations.Mult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.mingle.projmingle.Service.OrderService;
import com.ispan.mingle.projmingle.Service.ReviewService;
import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.ReviewBean;
import com.ispan.mingle.projmingle.domain.ReviewPhotoBean;
import com.ispan.mingle.projmingle.dto.ReviewDTO;
import com.ispan.mingle.projmingle.dto.ReviewReplyDTO;

import jakarta.servlet.http.Part;



@RestController
@RequestMapping("/review")
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReviewPhotoBean reviewPhoto;

    @Autowired
    private ReviewReplyDTO reviewReplyDTO;
    


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
    
    
    // 房東回應創建
    @PostMapping(path = "/create/reply", consumes = "multipart/form-data", produces = "application/json")
    public ReviewBean createReply(
            @RequestBody ReviewReplyDTO reply
        ) {
            try{
            ReviewBean temp = reviewService.findById(reply.getReviewid());
            temp.setReply(reply.getReply());
            temp.setReplyCreatedAt(reply.getReplyCreatedAt());
            temp.setReplyUpdatedAt(reply.getReplyUpdatedAt());
            

            if (reply.getPhoto() != null) {
                for (MultipartFile item : reply.getPhoto()) {
                byte[] photoBytes = item.getInputStream().readAllBytes();
                reviewPhoto.setPhoto(photoBytes);
                reviewPhoto.setContentType(item.getContentType());
                reviewPhoto.setReviewid(reply.getReviewid());
                reviewPhoto.setCreatedAt(reply.getReplyCreatedAt());
                reviewPhoto.setUpdatedAt(reply.getReplyUpdatedAt());
                reviewPhoto.setIsDeleted(false);
                reviewPhoto = reviewService.createReviewPhoto(reviewPhoto);
                }
            }
              reviewService.createReview(temp);
              return temp;
            
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    
    


}
