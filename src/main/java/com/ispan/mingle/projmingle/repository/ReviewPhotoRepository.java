package com.ispan.mingle.projmingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.ReviewPhotoBean;

public interface ReviewPhotoRepository extends JpaRepository<ReviewPhotoBean, Integer> {
    
}
