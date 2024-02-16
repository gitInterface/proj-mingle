package com.ispan.mingle.projmingle.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.ReportBean;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.repository.ReportRepository;
import com.ispan.mingle.projmingle.repository.WorkRepository;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private WorkRepository workRepository;


    /*
     * C
     */
    public ReportBean createReport(ReportBean reportBean) {
        return reportRepository.save(reportBean);
    }

    /*
     * R
     */
    public List<ReportBean> getAllReportsWithStatusZero() {
        return reportRepository.findByStatus(0);
    }

    public List<WorkBean> getWorksByReportBeans(List<ReportBean> reportBeans) {
    List<Integer> workIds = reportBeans.stream()
        .map(ReportBean::getWorkID)
        .collect(Collectors.toList());
    return workRepository.findAllById(workIds);
}
}
