package com.ispan.mingle.projmingle.controller;

import java.util.Base64;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.VolunteerDetailService;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.util.BaseUtil;

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

    @GetMapping("/Base64/{id}")
    public ResponseEntity<?> getVolunteerDetailWithBase64(@PathVariable String id) {
        // JSONObject responseJson = new JSONObject();
        VolunteerDetailBean vDBean = getVolunteerDetail(id);
        if (vDBean == null || vDBean.getIsDeleted()) {
            vDBean = null;
        } else {
            if (vDBean.getImage() != null) {
                byte[] imgbytes = vDBean.getImage();
                // img轉回base64
                vDBean.setPhotoBase64(BaseUtil.byteToBase64(vDBean.getPhotoType(), imgbytes));
                vDBean.setImage(Base64.getDecoder().decode("AA"));
            }
        }
        return ResponseEntity.ok().body(vDBean);
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

    // (工作頁面/workid找房東資訊)
    @GetMapping("/lordinfo/{lordid}")
    public ResponseEntity<VolunteerDetailBean> getMethodName(@PathVariable Integer lordid) {
        VolunteerDetailBean lordDetail = volunteerDetailService.findLordDetail(lordid);
        return ResponseEntity.ok(lordDetail);
    }
}
