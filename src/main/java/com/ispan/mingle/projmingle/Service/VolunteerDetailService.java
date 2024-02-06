package com.ispan.mingle.projmingle.Service;

import java.util.Base64;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.repository.VolunteerDetailRepository;
import com.ispan.mingle.projmingle.util.DatetimeConverter;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VolunteerDetailService {

    @Autowired
    private VolunteerDetailRepository volunteerDetailRepository;

    public VolunteerDetailBean findById(String id) {
        return volunteerDetailRepository.findById(id).orElse(null);
    }

    public VolunteerDetailBean update(String userid, String json) {
        // 接收使用者資料
        JSONObject job = new JSONObject(json);
        String update = job.isNull("update") ? null : job.getString("update");
        // 呼叫企業邏輯程式
        VolunteerDetailBean vDBean = volunteerDetailRepository.findById(userid)
                // 如果找不到資料就回傳一個空的資料
                .orElse(new VolunteerDetailBean());
        // 驗證使用者資料
        if (vDBean.getUserid() == null || !vDBean.getUserid().equals(userid)) {
            vDBean.setUserid(userid);
            vDBean.setCreatedAt(DatetimeConverter.getCurrentDate());
        }
        // 轉換資料
        if (update.equals("introductions")) {
            String introduction = job.isNull("introduction") ? null : job.getString("introduction");
            String background = job.isNull("background") ? null : job.getString("background");
            String language = job.isNull("language") ? null : job.getString("language");
            String hobby = job.isNull("hobby") ? null : job.getString("hobby");
            // 輸入使用者所要更新的資料
            vDBean.setIntroduction(introduction);
            vDBean.setBackground(background);
            vDBean.setLanguage(language);
            vDBean.setHobby(hobby);
        } else if (update.equals("details")) {
            String name = job.isNull("name") ? null : job.getString("name");
            String gender = job.isNull("gender") ? null : job.getString("gender");
            String phone = job.isNull("phone") ? null : job.getString("phone");
            String email = job.isNull("email") ? null : job.getString("email");
            String birth = job.isNull("birth") ? null : job.getString("birth");
            String country = job.isNull("country") ? null : job.getString("country");
            // 輸入使用者所要更新的資料
            if (name != null)
                vDBean.setName(name);
            if (gender != null)
                vDBean.setGender(gender);
            if (phone != null)
                vDBean.setPhone(phone);
            if (email != null)
                vDBean.setEmail(email);
            if (birth != null)
                vDBean.setBirth(DatetimeConverter.parse(birth, "yyyy-MM-dd"));
            if (country != null)
                vDBean.setCountry(country);
        } else if (update.equals("photo")) {
            String photoType = job.isNull("photoType") ? null : job.getString("photoType");
            Integer photoSize = job.isNull("photoSize") ? null : job.getInt("photoSize");
            String photo = job.isNull("photo") ? null : job.getString("photo");
            byte[] imageBytes = null;
            // 驗證照片資料
            if (photo == null || photo.isEmpty() || photo == "" || photo.length() < 100) {
                return null;
            }
            // 轉換照片成Byte[]
            imageBytes = Base64.getDecoder().decode(photo);
            // 輸入使用者所要更新的資料
            vDBean.setPhotoType(photoType);
            vDBean.setPhotoSize(photoSize);
            vDBean.setImage(imageBytes);
        } else {
            return null;
        }
        vDBean.setUpdatedAt(DatetimeConverter.getCurrentDate());
        // 更新使用者資料
        VolunteerDetailBean newBean = volunteerDetailRepository.save(vDBean);
        return newBean;
    }

    public boolean exists(String id) {
        return volunteerDetailRepository.existsById(id);
    }
}
