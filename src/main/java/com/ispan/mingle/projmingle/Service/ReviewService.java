package com.ispan.mingle.projmingle.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.HousePhotoBean;
import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.ReviewBean;
import com.ispan.mingle.projmingle.domain.ReviewPhotoBean;
import com.ispan.mingle.projmingle.repository.LandlordRepository;
import com.ispan.mingle.projmingle.repository.OrderRepository;
import com.ispan.mingle.projmingle.repository.ReviewPhotoRepository;
import com.ispan.mingle.projmingle.repository.ReviewRepository;
import com.ispan.mingle.projmingle.repository.VolunteerRepository;
import com.ispan.mingle.projmingle.repository.WorkRepository;
import com.ispan.mingle.projmingle.util.BaseUtil;

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

    @Autowired
    private ReviewPhotoRepository reviewPhotoRepository;

    // 依據星數由高到低排序取得指定數量的評價
    public Page<ReviewBean> getReviewsByStarsDesc(int limit) {
        return reviewRepository.findAllByOrderByStarsDesc(PageRequest.of(0, limit));
    }

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


    /** 送出房東文字回應 */
    public ReviewBean createReview(ReviewBean bean){
        return reviewRepository.save(bean);
    }


    /** 送出房東圖片回應 */
    public ReviewPhotoBean createReviewPhoto(ReviewPhotoBean bean){
        return reviewPhotoRepository.save(bean);
    }


    /** 透過評價id查詢評論 */
    public ReviewBean findById(Integer reviewId){
        ReviewBean review = reviewRepository.findById(reviewId).orElse(null);
        return review;
    }

    /** 透過評論id查詢評論照片 */

    public List<String> findReviewPhotoByReviewId(Integer reviewId){
        List<ReviewPhotoBean> reviewPhotos = reviewPhotoRepository.findAllByReviewId(reviewId);
        List<String> imageList = new ArrayList<>();
        for (ReviewPhotoBean reviewPhoto : reviewPhotos) {
            String basePhoto = "data:image/" + reviewPhoto.getContentType() + ";base64,"
                        + Base64.getEncoder().encodeToString(reviewPhoto.getPhoto());
                imageList.add(basePhoto);
        };
        return imageList;
        
    }



}
