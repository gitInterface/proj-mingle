package com.ispan.mingle.projmingle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.ReportService;
import com.ispan.mingle.projmingle.domain.ReportBean;

@RestController
@RequestMapping("/api/report")
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/addReport")
    public ReportBean createReport(@RequestBody ReportBean reportBean) {
        return reportService.createReport(reportBean);
    }
}