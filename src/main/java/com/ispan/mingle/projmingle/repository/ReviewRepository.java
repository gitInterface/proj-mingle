package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.mingle.projmingle.domain.ReviewBean;

public interface ReviewRepository extends JpaRepository<ReviewBean, Integer> {
    

    
}
