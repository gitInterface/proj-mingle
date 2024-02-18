package com.ispan.mingle.projmingle.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

import com.ispan.mingle.projmingle.Service.WorkService;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.dto.WorkCreateDTO;
import com.ispan.mingle.projmingle.dto.WorkModifyDTO;
import com.ispan.mingle.projmingle.dto.WorkModifyHouseDTO;
import com.ispan.mingle.projmingle.dto.WorkModifyListDTO;
import com.ispan.mingle.projmingle.dto.WorkModifySubmitWorkDTO;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/work")
@CrossOrigin
public class WorkController {
    @Autowired
    private WorkService workService;

    /*
     * C
     */

    @PostMapping("/addWork")
    public void getWorks(@RequestBody WorkCreateDTO workDTO) {
        workService.setNewWork(workDTO);
    }

    /*
     * R
     */

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

    // 查詢待審核工作數量
    @GetMapping("countPendingReview")
    public ResponseEntity<Integer> countPendingReview() {
        return ResponseEntity.ok(workService.countPendingReview());
    }

    // 查詢某個地址的所有work
    @GetMapping("/getWorksByAddress/{address}")
    public ResponseEntity<List<WorkBean>> getWorksByAddress(@PathVariable String address) {
        List<WorkBean> works = workService.getWorksByAddress(address);
        return ResponseEntity.ok(works);
    }

    // 查詢格式化後的地址
    @GetMapping("/formattedAddresses")
    public ResponseEntity<List<String>> getFormattedAddresses() {
        List<String> formattedAddresses = workService.getFormattedAddresses();
        return ResponseEntity.ok(formattedAddresses);
    }

    // (工作管理/修改渲染)workid查詢work, workPhoto, work_house, house, housePhoto
    @GetMapping("/modifyWork/show/{workid}")
    public ResponseEntity<WorkModifyDTO> getWorkAllInfo(@PathVariable Integer workid) {
        return ResponseEntity.ok(workService.showModify(workid));

    }

    // (工作管理/修改渲染) 房子分開渲染，資料太多有點慢
    @GetMapping("/modifyWork/showHouse/{workid}")
    public ResponseEntity<WorkModifyHouseDTO> getHouseAllInfo(@PathVariable Integer workid) {
        return ResponseEntity.ok(workService.showModifyHouse(workid));
    }

    // (工作管理/修改提交) 工作
    @PutMapping("/modifyWork/submitWork/{workid}")
    public void submitWork(@RequestBody WorkModifySubmitWorkDTO requestWork, @PathVariable Integer workid) {
        if (requestWork != null && workid != null) {
            workService.workModifyWork(requestWork, workid);
        }
    }

    // (工作管理/修改提交) 房子
    @PutMapping(path = "/modifyWork/submitHouse/{workid}", consumes = "multipart/form-data")
    public void submitHouse(
            @RequestParam(required = false) List<MultipartFile> newList, @PathVariable Integer workid) {
        if (newList != null) {
            // 條件不分開寫會爛掉(null沒有size方法)
            if (newList.size() != 0) {
                workService.workModifyPhoto(newList, workid);
            }
        }
    }

    // (工作管理/列表渲染)
    @GetMapping("modifyWork/workList/{lordid}")
    public ResponseEntity<List<WorkModifyListDTO>> showWorkList(@PathVariable Integer lordid) {
        return ResponseEntity.ok(workService.showWorkList(lordid));
    }

    // (工作管理/刪除)
    @PutMapping("modifyWork/delete/{workid}")
    public void deleteWork(@PathVariable Integer workid) {
        if (workid != null) {
            workService.deleteWork(workid);
        }
    }

    /*
     * U
     */
    // 增加某workid的瀏覽量
    @PostMapping("/increaseViewCount/{workid}")
    public ResponseEntity<Void> increaseViewCount(@PathVariable Integer workid) {
        workService.increaseViewCount(workid);
        return ResponseEntity.ok().build();
    }

    // 修改某workid的狀態
    @PutMapping("/updateWorkStatus/{workid}")
    public ResponseEntity<Void> updateWorkStatus(@PathVariable Integer workid, @RequestParam String status, @RequestParam Boolean isDeleted) {
        workService.updateWorkStatus(workid, status, isDeleted);
        return ResponseEntity.ok().build();
    }
}