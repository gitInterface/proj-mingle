package com.ispan.mingle.projmingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.OrderBean;

public interface OrderRepository extends JpaRepository<OrderBean, Integer> {

    
} 