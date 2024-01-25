package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.domain.WorkBean;

public interface WorkRepository extends JpaRepository<WorkBean, Integer>, JpaSpecificationExecutor<WorkBean> , WorkSpringDataJpaDAO{
    Page<WorkBean> findAll(Specification<WorkBean> spec, Pageable pageable);
    
@Query("SELECT h FROM WorkBean w LEFT JOIN WorkHouseBean wh ON w.workid = wh.workid LEFT JOIN HouseBean h ON wh.houseid = h.houseid WHERE w.workid = :workId")
public List<HouseBean> findHousesByWorkId(@Param("workId") Integer workId);
}
