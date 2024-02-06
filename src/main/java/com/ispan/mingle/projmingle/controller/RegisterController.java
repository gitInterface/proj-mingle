package com.ispan.mingle.projmingle.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.VolunteerService;
import com.ispan.mingle.projmingle.domain.VolunteerBean;

@RestController
@CrossOrigin
public class RegisterController {
    @Autowired
	private VolunteerService volunteerService;

    @PostMapping("/register.controller")
    public String create(@RequestBody String body) {
        JSONObject responseJson = new JSONObject();

        JSONObject obj = new JSONObject(body);
        String username = obj.isNull("username") ? null : obj.getString("username");
        String password = obj.isNull("password") ? null : obj.getString("password");
        if(username.equals(null) || username.equals("")) {
            responseJson.put("message", "使用者名稱是必要欄位");
            responseJson.put("success", false);
        // }else if(!username.contains("@")){
        //     responseJson.put("message", "Id必須是郵件信箱");
        //     responseJson.put("success", false);
        }else if(password.equals(null) || password.equals("")) {
            responseJson.put("message", "密碼是必要欄位");
            responseJson.put("success", false);
        }else if(username.contains(" ") || password.contains(" ")){
            responseJson.put("message", "不可有空白");
            responseJson.put("success", false);
        }else if(volunteerService.existsByUsername(username)) {
            responseJson.put("message", "使用者名稱已存在");
            responseJson.put("success", false);
        } else {
            VolunteerBean result = volunteerService.create(body);
            if(result==null) {
                responseJson.put("message", "新增失敗");
                responseJson.put("success", false);
            } else {
                responseJson.put("message", "新增成功");
                responseJson.put("success", true);
            }
        }

        return responseJson.toString();
    }
}
