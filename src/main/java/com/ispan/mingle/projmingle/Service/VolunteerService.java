package com.ispan.mingle.projmingle.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.VolunteerBean;
import com.ispan.mingle.projmingle.repository.VolunteerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VolunteerService {
	@Autowired
	private VolunteerRepository volunteerRepository = null;

	public VolunteerBean login(String userid, String password) {
		VolunteerBean select = volunteerRepository.select(userid);
		if (select != null) {
			if (password != null) {
				String pass = select.getPassword(); // 資料庫取出
				// 使用者輸入(password)
				if (pass.equals(password)) {
					return select;
				}
			}
		}
		return null;
	}

	public boolean exists(String userid) {
		if(userid!=null) {
			return volunteerRepository.existsById(userid);
		}
		return false;
	}

	// 檢查用戶有無管理員權限
	public Boolean isAdmin(String userid) {
        VolunteerBean volunteer = volunteerRepository.findByUserid(userid);
        return volunteer != null && Boolean.TRUE.equals(volunteer.getIsAdmin());
    }

	public VolunteerBean create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			String userid = obj.isNull("userid") ? null : obj.getString("userid");
			String password = obj.isNull("password") ? null : obj.getString("password");
			
			if(userid!=null) {
				VolunteerBean insert = new VolunteerBean();
				insert.setUserid(userid);
				insert.setPassword(password);
				insert.setIsAdmin(false);
				
				return volunteerRepository.save(insert);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}	