package com.ispan.mingle.projmingle.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.VolunteerDetailService;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;

@RestController
@RequestMapping("/api/volunteerDetail")
@CrossOrigin
public class VolunteerDetailController {

    @Autowired
    private VolunteerDetailService volunteerDetailService;

    @GetMapping("/{id}")
    public VolunteerDetailBean getVolunteerDetail(@PathVariable String id) {
        return volunteerDetailService.findById(id);
    }

    @PatchMapping("/update/details/{pk}")
    public String updateIntroductions(@PathVariable(value = "pk") String id, @RequestBody String body) {

        // System.err.println("執行到這裡了嗎?");

        // 準備回傳的資料
        JSONObject responseJson = new JSONObject();
        if (id == null || id.isEmpty()) {
            responseJson.put("message", "Id是必要欄位");
            responseJson.put("success", false);
        } else if (!volunteerDetailService.exists(id)) {
            responseJson.put("message", "Id不存在");
            responseJson.put("success", false);
        } else {
            VolunteerDetailBean newBean = volunteerDetailService.update(id, body);
            if (newBean == null) {
                responseJson.put("message", "修改失敗 ,輸入資料不正確");
                responseJson.put("success", false);
            } else {
                responseJson.put("message", "修改成功");
                responseJson.put("success", true);
                responseJson.put("data", newBean);
            }
        }
        return responseJson.toString();
    }
}
