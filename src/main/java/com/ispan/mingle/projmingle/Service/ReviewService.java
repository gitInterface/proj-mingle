package com.ispan.mingle.projmingle.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.ReviewBean;
import com.ispan.mingle.projmingle.repository.LandlordRepository;
import com.ispan.mingle.projmingle.repository.OrderRepository;
import com.ispan.mingle.projmingle.repository.ReviewRepository;
import com.ispan.mingle.projmingle.repository.VolunteerRepository;
import com.ispan.mingle.projmingle.repository.WorkRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReviewService {
 
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired 
    private WorkRepository workRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;


    /** 用工作id找出評論 */
    public LandlordBean getLandlordByWorkId(Integer workId){
        LandlordBean landlord = workRepository.findLandlordByWorkId(workId);
        return landlord;
    }


    /** 用房東id找出訂單 */
    public  List<OrderBean> getOrderByLandlordId(Integer landlordId){
        List<OrderBean> order  = landlordRepository.findOrderByLandlordId(landlordId);
        return order;
    }  


    /** 用房東id找出評論 */
    public List<ReviewBean> getReviewByLandlordId(Integer landlordId){
       List<ReviewBean> review = landlordRepository.findReviewByLandlordId(landlordId);
       return review;
    }

    /** 用userid查房東id */
    public LandlordBean getLandlordByUserid(String userid){
        LandlordBean landlord = volunteerRepository.findLandlordByUserid(userid);
        return landlord;
    }


    /** 送出房東回應 */
    public ReviewBean createReview(ReviewBean bean){
        return reviewRepository.save(bean);
    }

    /** 透過評價id查詢評論 */
    public ReviewBean findById(Integer reviewId){
        ReviewBean review = reviewRepository.findById(reviewId).orElse(null);
        return review;
    }
}
