package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.mingle.projmingle.domain.ReviewPhotoBean;

public interface ReviewPhotoRepository extends JpaRepository<ReviewPhotoBean, Integer> {
    
    @Query("SELECT r FROM ReviewPhotoBean r WHERE r.reviewid = :reviewid")
    public List<ReviewPhotoBean> findAllByReviewId(@Param("reviewid") Integer reviewid);
}
