package com.ispan.mingle.projmingle.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.ReportBean;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.repository.ReportRepository;
import com.ispan.mingle.projmingle.repository.WorkRepository;
import com.ispan.mingle.projmingle.util.BaseUtil;

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
                .map(ReportBean::getWorkID).collect(Collectors.toList());
        List<WorkBean> works = workRepository.findAllById(workIds);
        for (WorkBean work : works) {
            List<String> photosBase64 = work.getUndeletedWorkPhotoBeans().stream()
                    .limit(6)
                    .map(photo -> BaseUtil.byteToBase64(photo.getContentType(),
                            photo.getPhoto()))
                    .collect(Collectors.toList());
            work.setPhotosBase64(photosBase64);
        }
        return works;
    }

    // 查詢待審核檢舉數量
    public Integer countPendingReport() {
        return reportRepository.findPendingReport();
    }

}
