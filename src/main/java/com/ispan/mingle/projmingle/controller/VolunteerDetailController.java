package com.ispan.mingle.projmingle.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.VolunteerDetailService;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/update/introductions")
    public String postMethodName(@RequestBody String body) {

        // System.err.println("執行到這裡了嗎?");

        // 接收使用者資料
        JSONObject job = new JSONObject(body);
        String userid = job.isNull("userid") ? null : job.getString("userid");
        String introduction = job.isNull("introduction") ? null : job.getString("introduction");
        String background = job.isNull("background") ? null : job.getString("background");
        String language = job.isNull("language") ? null : job.getString("language");
        String hobby = job.isNull("hobby") ? null : job.getString("hobby");
        // 準備回傳的資料
        JSONObject responseJson = new JSONObject();
        // 驗證使用者資料
        if (userid == null || userid.length() == 0) {
            responseJson.put("error", "沒有userid");
            return responseJson.toString();
        }
        // if (introduction == null || background == null || language == null || hobby
        // == null
        // || introduction.length() == 0 || background.length() == 0 ||
        // language.length() == 0
        // || hobby.length() == 0) {
        // responseJson.put("error", "有資料沒輸入");
        // }

        // 轉換資料
        // 呼叫企業邏輯程式
        VolunteerDetailBean volunteerDetailBean = volunteerDetailService.findById(userid);
        if (volunteerDetailBean == null) {
            volunteerDetailBean = new VolunteerDetailBean();
        }
        volunteerDetailBean.setIntroduction(introduction);
        volunteerDetailBean.setBackground(background);
        volunteerDetailBean.setLanguage(language);
        volunteerDetailBean.setHobby(hobby);

        // 更新使用者資料
        VolunteerDetailBean update = volunteerDetailService.update(volunteerDetailBean);
        responseJson.put("success", update);

        return responseJson.toString();
    }

}
