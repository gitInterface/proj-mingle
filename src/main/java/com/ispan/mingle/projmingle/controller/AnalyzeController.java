package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.AnalyzeService;
import com.ispan.mingle.projmingle.dto.AnalyzeDTO;

@RestController
@RequestMapping("/analyze")
@CrossOrigin
public class AnalyzeController {

    @Autowired
    private AnalyzeService analyzeService;

    /** 透過房東id取得資料 */
    @GetMapping("/detail")
    public AnalyzeDTO getAnalyzeByLandlordId(@RequestParam("landlordId") Integer landlordId) {
        return analyzeService.getAnalyzeByLandlordId(landlordId);

    }

    /** 用房東id找每年每月訂單數 */

    @GetMapping("/monthly")
    public List<Object[]> findMonthlyOrderCountByLandlordId(@RequestParam("landlordId") Integer landlordId) {

        return analyzeService.findMonthlyOrderCountByLandlordId(landlordId);

    }

    /** 用房東id找每個工作訂單數 */

    @GetMapping("/workordercount")
    public List<Object[]> findWorkOrderCountByLandlordId(@RequestParam("landlordId") Integer landlordId) {

        return analyzeService.findWorkOrderCountByLandlordId(landlordId);

    }

    /** 透過房東id查詢每個工作的瀏覽量 */
    @GetMapping("/workviews")
    public List<Object[]> findWorkViewsByLandlordId(@RequestParam("landlordId") Integer landlordId) {

        return analyzeService.findWorkViewsByLandlordId(landlordId);
    }

     /** 房東每個工作分別評價數及評分 */
    @GetMapping("/workreview")
    public List<Object[]> findWorkReviewByLandlordId(@RequestParam("landlordId") Integer landlordId) {

        return analyzeService.findWorkReviewByLandlordId(landlordId);
    }

}
