package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.ReportService;
import com.ispan.mingle.projmingle.domain.ReportBean;
import com.ispan.mingle.projmingle.domain.WorkBean;

@RestController
@RequestMapping("/api/report")
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    /*
     * C
     */
    @PostMapping("/addReport")
    public ReportBean createReport(@RequestBody ReportBean reportBean) {
        return reportService.createReport(reportBean);
    }

    /*
     * R
     */
    @GetMapping("/statusZero")
    public List<ReportBean> getAllReportsWithStatusZero() {
        return reportService.getAllReportsWithStatusZero();
    }

    @PostMapping("/worksByReportBeans")
    public List<WorkBean> getWorksByReportBeans(@RequestBody List<ReportBean> reportBeans) {
        return reportService.getWorksByReportBeans(reportBeans);
    }

    @GetMapping("/countPendingReport")
    public Integer countPendingReport() {
        return reportService.countPendingReport();
    }

    /*
     * U
     */
    @PutMapping("/updateReportStatus")
    public ReportBean updateReportStatus(@RequestParam Integer reportID, @RequestParam Integer status) {
        return reportService.updateReportStatus(reportID, status);
    }
    
}