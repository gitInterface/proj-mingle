package com.ispan.mingle.projmingle.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.VolunteerDetailService;
import com.ispan.mingle.projmingle.Service.VolunteerService;
import com.ispan.mingle.projmingle.domain.VolunteerBean;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;

@RestController
@CrossOrigin
public class PasswordResetController {
    @Autowired
    private VolunteerDetailService volunteerDetailService;

    @Autowired
    private VolunteerService volunteerService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email parameter is missing");
        }
        VolunteerDetailBean volunteerDetail = volunteerDetailService.findByEmail(email);
        if (volunteerDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        // Send password reset instructions via email
        volunteerDetailService.sendPasswordResetEmail(volunteerDetail);
        return ResponseEntity.ok("Password reset instructions sent successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String newPassword = requestBody.get("password");
        if (email == null || newPassword == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or newPassword parameter is missing");
        }
        VolunteerBean volunteer = volunteerDetailService.findVolunteerByEmail(email);
        if (volunteer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        // Update the password
        volunteer.setPassword(newPassword);
        volunteerService.save(volunteer);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PatchMapping("/resetPassword/byId")
    public String resetPasswordById(@RequestBody String body) {
        // 準備回傳的資料
        JSONObject responseJson = new JSONObject();
        VolunteerBean newBean = volunteerService.updatePasswordById(body);
        if (newBean == null) {
            responseJson.put("message", "修改失敗 ,輸入資料不正確");
            responseJson.put("success", false);
        } else {
            responseJson.put("message", "修改成功");
            responseJson.put("success", true);
        }
        return responseJson.toString();
    }

}
