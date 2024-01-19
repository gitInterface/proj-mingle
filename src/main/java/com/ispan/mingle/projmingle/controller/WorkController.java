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

    @GetMapping("/getAllWorks")
    public List<WorkBean> getAllWorks() {
        return workRepository.findAll();
    }

    @GetMapping("/getHotWorks")
    public List<WorkBean> getHotWorks() {
        return workService.getHotWorks();
    }

    @GetMapping("/getLatestWorks")
    public List<WorkBean> getLatestWorks() {
        return workService.getLatestWorks();
    }

    @GetMapping("/getOldestWorks")
    public List<WorkBean> getOldestWorks() {
        return workService.getOldestWorks();
    }

    @GetMapping("/getDeadlineWorks")
    public List<WorkBean> getDeadlineWorks() {
        return workService.getDeadlineWorks();
    }

    @GetMapping("/getWorksByRemainingSpotsAsc")
    public List<WorkBean> getWorksByRemainingSpotsAsc() {
        return workService.getWorksByRemainingSpotsAsc();
    }

    @GetMapping("/getWorksByRemainingSpotsDesc")
    public List<WorkBean> getWorksByRemainingSpotsDesc() {
        return workService.getWorksByRemainingSpotsDesc();
    }

    @GetMapping("/formattedAddresses")
    public ResponseEntity<List<String>> getFormattedAddresses() {
        List<String> formattedAddresses = workService.getFormattedAddresses();
        return ResponseEntity.ok(formattedAddresses);
    }
}