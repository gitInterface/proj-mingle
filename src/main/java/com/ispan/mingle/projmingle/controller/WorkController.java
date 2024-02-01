package com.ispan.mingle.projmingle.controller;

import java.util.List;
import java.util.Map;

import com.ispan.mingle.projmingle.dto.WorkCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.WorkService;
import com.ispan.mingle.projmingle.domain.WorkBean;

@RestController
@RequestMapping("/api/work")
@CrossOrigin
public class WorkController {
    @Autowired
    private WorkService workService;

    // @GetMapping("/getAllWorks")
    // public List<WorkBean> getAllWorks() {
    // return workRepository.findAll();

    // 依據查詢條件獲取工作
    @PostMapping("/getWorks")
    public Page<WorkBean> getWorks(Pageable pageable, @RequestParam String direction, @RequestParam String property,
            @RequestBody Map<String, List<String>> filterMap) {

        return workService.getWorks(pageable, direction, property, filterMap);
    }

    // 依據workid獲取工作
    @GetMapping("/getWork/{workid}")
    public WorkBean getWork(@PathVariable Integer workid) {
        return workService.getWork(workid);
    }
    
    @GetMapping("/formattedAddresses")
    public ResponseEntity<List<String>> getFormattedAddresses() {
        List<String> formattedAddresses = workService.getFormattedAddresses();
        return ResponseEntity.ok(formattedAddresses);
    }

    @PostMapping("/addWork")
    public void getWorks(@RequestBody WorkCreateDTO workDTO )  {
        workService.setNewWork(workDTO);
    }
}