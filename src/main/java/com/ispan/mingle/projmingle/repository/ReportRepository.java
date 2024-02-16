package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.ReportBean;

public interface ReportRepository extends JpaRepository<ReportBean, Integer> {
    public List<ReportBean> findByStatus(int status);
}