package com.ispan.mingle.projmingle.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.ReviewBean;

public interface ReviewRepository extends JpaRepository<ReviewBean, Integer> {
    
    Page<ReviewBean> findAllByOrderByStarsDesc(Pageable pageable);
    
}
