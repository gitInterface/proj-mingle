package com.ispan.mingle.projmingle.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.ispan.mingle.projmingle.util.FileUtil;

@RestController
@RequestMapping("/review")
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReviewReplyDTO reviewReplyDTO;

    @GetMapping("/getReviewsByStarsDesc")
    public Page<ReviewBean> getReviewsByStarsDesc(@RequestParam int limit) {
        return reviewService.getReviewsByStarsDesc(limit);
    }

    /** 用房東id找出訂單 */
    @GetMapping("/findorder")
    public List<OrderBean> getOrderByLandlordId(@RequestParam Integer landlordId) {
        List<OrderBean> order = reviewService.getOrderByLandlordId(landlordId);
        return order;
    }

    /** 根據房東id取得評論 */
    @GetMapping("/findreview")
    public List<ReviewBean> getReviewByLandlordId(@RequestParam Integer landlordId) {
        List<ReviewBean> reviewBeans = reviewService.getReviewByLandlordId(landlordId);
        return reviewBeans;
    }

    /** 根據評論id找出評論照片 */
    @GetMapping("/findReviewPhotos")
    public List<String> getReviewPhotosByReviewId(@RequestParam(name="reviewId") Integer reviewId) {
        List<String> reviewPhotos = reviewService.findReviewPhotoByReviewId(reviewId);
        return reviewPhotos;
    }


    @GetMapping("/findLandlord")
    public LandlordBean getLandlord(@RequestParam String userid) {
        return reviewService.getLandlordByUserid(userid);
    }

    @GetMapping("/findOrderDetail")
    public ReviewDTO getOrderDetail(@RequestParam Integer orderid) {
        return orderService.findReviewDTOByOrderId(orderid);
    }

    // 房東回應創建
    @PostMapping(path = "/create/reply")
    public ReviewBean createReply(
    @RequestBody ReviewReplyDTO reply
    )
            {
     
            ReviewBean temp = reviewService.findById(reply.getReviewid());
            temp.setReply(reply.getReply());
            temp.setReplyCreatedAt(reply.getReplyCreatedAt());
            temp.setReplyUpdatedAt(reply.getReplyUpdatedAt());
            reviewService.createReview(temp);
            return temp;

       
    }


    // 房客回應創建
    @PostMapping(path = "/create/review")
    public ReviewBean createReview(
            @RequestBody ReviewBean review
    ){
        return reviewService.createReview(review);
    }

    // 房客回應照片創建
    @PostMapping(path = "/create/review/photo", consumes = "multipart/form-data")
    public ReviewPhotoBean createReviewPhoto(
            @RequestParam(name="reviewid") Integer reviewid,
            @RequestParam(name="replyCreatedAt") Date replyCreatedAt,
            @RequestParam(name="replyUpdatedAt") Date replyUpdatedAt,
            @RequestParam(required = false, name = "photo") List<MultipartFile> photo
    ){
 try {
        if (photo != null) {
            for (MultipartFile multipartFile : photo) {
                File file = FileUtil.convertMultipartFileToFile(multipartFile);

                ReviewPhotoBean reviewPhoto = new ReviewPhotoBean();

                reviewPhoto.setReviewid(reviewid);
                // 副檔名切割(ex. image/jpeg -> jpeg)
                String contentType = multipartFile.getContentType();
                String[] parts = contentType.split("/");
                reviewPhoto.setContentType(parts[1]);
                FileInputStream fis = new FileInputStream(file);
                reviewPhoto.setPhoto(fis.readAllBytes());
                reviewPhoto.setCreatedAt(replyCreatedAt);
                reviewPhoto.setUpdatedAt(replyUpdatedAt);
                reviewPhoto.setIsDeleted(false);
                reviewPhoto = reviewService.createReviewPhoto(reviewPhoto);

                fis.close();
                file.delete();
                return reviewPhoto;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
        return null;
        
    }
}
