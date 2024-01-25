package com.ispan.mingle.projmingle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.mingle.projmingle.domain.WorkBean;

public interface WorkRepository extends JpaRepository<WorkBean, Integer>, JpaSpecificationExecutor<WorkBean> , WorkSpringDataJpaDAO{
    Page<WorkBean> findAll(Specification<WorkBean> spec, Pageable pageable);
}
