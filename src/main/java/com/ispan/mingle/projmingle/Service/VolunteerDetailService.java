package com.ispan.mingle.projmingle.Service;

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
            vDBean.setName(name);
            vDBean.setGender(gender);
            vDBean.setPhone(phone);
            vDBean.setEmail(email);
            vDBean.setBirth(DatetimeConverter.parse(birth, "yyyy-MM-dd"));
            vDBean.setCountry(country);
        } else {
            return null;
        }

        // 更新使用者資料
        VolunteerDetailBean newBean = volunteerDetailRepository.save(vDBean);
        return newBean;
    }

    public boolean exists(String id) {
        return volunteerDetailRepository.existsById(id);
    }
}
