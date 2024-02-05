package com.ispan.mingle.projmingle.controller;

import java.util.List;
import java.util.Map;

import com.ispan.mingle.projmingle.dto.WorkCreateDTO;
import com.ispan.mingle.projmingle.dto.WorkModifyDTO;

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
    public Page<WorkBean> getWorks(Pageable pageable, @RequestParam(required = false) String direction,
            @RequestParam(required = false) String property, @RequestParam(required = false) String userID,
            @RequestBody Map<String, ?> filterMap) {

        return workService.getWorks(pageable, direction, property, filterMap, userID);
    }

    // 依據某個workid獲取工作
    @GetMapping("/getWork/{workid}")
    public WorkBean getWork(@PathVariable Integer workid) {
        return workService.getWork(workid);
    }

    // 增加某workid的瀏覽量
    @PostMapping("/increaseViewCount/{workid}")
    public ResponseEntity<Void> increaseViewCount(@PathVariable Integer workid) {
        workService.increaseViewCount(workid);
        return ResponseEntity.ok().build();
    }

    // 查詢某個地址的所有work
    @GetMapping("/getWorksByAddress/{address}")
    public ResponseEntity<List<WorkBean>> getWorksByAddress(@PathVariable String address) {
        List<WorkBean> works = workService.getWorksByAddress(address);
        return ResponseEntity.ok(works);
    }

    @GetMapping("/formattedAddresses")
    public ResponseEntity<List<String>> getFormattedAddresses() {
        List<String> formattedAddresses = workService.getFormattedAddresses();
        return ResponseEntity.ok(formattedAddresses);
    }

    @PostMapping("/addWork")
    public void getWorks(@RequestBody WorkCreateDTO workDTO) {
        workService.setNewWork(workDTO);
    }

    // (工作管理渲染)workid查詢work, workPhoto, work_house, house, housePhoto
    @GetMapping("/modifyWork/show/{workid}")
    public ResponseEntity<WorkModifyDTO> getWorkAllInfo(@PathVariable Integer workid) {
        return ResponseEntity.ok(workService.showModify(workid));

    }
}