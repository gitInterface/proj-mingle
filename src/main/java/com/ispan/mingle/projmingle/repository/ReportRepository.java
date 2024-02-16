package com.ispan.mingle.projmingle.repository;

import com.ispan.mingle.projmingle.domain.ReportBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportBean, Integer> {
}