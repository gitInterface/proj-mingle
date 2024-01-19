package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.WorkService;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.repository.WorkRepository;

@RestController
@RequestMapping("/api/work")
@CrossOrigin
public class WorkController {
    @Autowired
    private WorkService workService;

    @Autowired
    private WorkRepository workRepository;

    @GetMapping("/all")
    public ResponseEntity<List<WorkBean>> getAllWorkBeans() {
        List<WorkBean> workBeans = workService.getAllWorkBeans();
        return ResponseEntity.ok(workBeans);
    }

    @GetMapping("/formattedAddresses")
    public ResponseEntity<List<String>> getFormattedAddresses() {
        List<String> formattedAddresses = workService.getFormattedAddresses();
        return ResponseEntity.ok(formattedAddresses);
    }

    @GetMapping("/getAllWorks")
    public List<WorkBean> getAllWorks() {
        return workRepository.findAll();
    }
}