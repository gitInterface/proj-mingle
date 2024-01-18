package com.ispan.mingle.projmingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.WorkBean;

public interface WorkRepository extends JpaRepository<WorkBean, Integer>, WorkSpringDataJpaDAO{
    
}
