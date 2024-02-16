package com.ispan.mingle.projmingle.Service;

import com.ispan.mingle.projmingle.domain.ReportBean;
import com.ispan.mingle.projmingle.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public ReportBean createReport(ReportBean reportBean) {
        return reportRepository.save(reportBean);
    }
}
