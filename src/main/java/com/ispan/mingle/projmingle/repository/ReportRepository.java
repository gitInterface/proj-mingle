package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.mingle.projmingle.domain.ReportBean;

public interface ReportRepository extends JpaRepository<ReportBean, Integer> {
    public List<ReportBean> findByStatus(int status);

    @Query("SELECT COUNT(r) FROM ReportBean r WHERE r.status = 0")
    public Integer findPendingReport();
}