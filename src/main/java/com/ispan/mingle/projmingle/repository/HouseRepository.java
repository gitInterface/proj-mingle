package com.ispan.mingle.projmingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.HouseBean;

public interface HouseRepository extends JpaRepository<HouseBean, Integer>, HouseSpringDataJpaDAO{
    
}
