package com.ispan.mingle.projmingle.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.KeepWorkBean;
import com.ispan.mingle.projmingle.domain.KeepWorkId;
import com.ispan.mingle.projmingle.domain.VolunteerBean;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.repository.KeepWorkRepository;
import com.ispan.mingle.projmingle.repository.VolunteerRepository;
import com.ispan.mingle.projmingle.repository.WorkRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VolunteerService {
	@Autowired
	private KeepWorkRepository keepWorkRepository;
	@Autowired
	private VolunteerRepository volunteerRepository;
	@Autowired
	private WorkRepository workRepository;

	/*
	 * CREATE
	 */
	// 新建用戶
	public VolunteerBean create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			String userid = obj.isNull("userid") ? null : obj.getString("userid");
			String password = obj.isNull("password") ? null : obj.getString("password");

			if (userid != null) {
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

	/*
	 * READ
	 */
	// 使用者登入
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
		if (userid != null) {
			return volunteerRepository.existsById(userid);
		}
		return false;
	}

	// 檢查用戶有無管理員權限
	public Boolean isAdmin(String userid) {
		VolunteerBean volunteer = volunteerRepository.findByUserid(userid);
		return volunteer != null && Boolean.TRUE.equals(volunteer.getIsAdmin());
	}

	// 查詢用戶是否有收藏該工作
	public boolean isWorkKeptByVolunteer(String volunteerId, Integer workId) {
		return keepWorkRepository.existsByVolunteer_UseridAndWork_Workid(volunteerId, workId);
	}

	/*
	 * UPDATE
	 */
	public void addWorkToKeepList(String volunteerId, Integer workId) {
		VolunteerBean volunteer = volunteerRepository.findById(volunteerId).orElse(null);
		WorkBean work = workRepository.findById(workId).orElse(null);

		if (volunteer != null && work != null) {
			KeepWorkBean keepWork = new KeepWorkBean();
			keepWork.setVolunteer(volunteer);
			keepWork.setWork(work);
			keepWorkRepository.save(keepWork);
		}
	}

	public void removeWorkFromKeepList(String volunteerId, Integer workId) {
		VolunteerBean volunteer = volunteerRepository.findById(volunteerId).orElse(null);
		WorkBean work = workRepository.findById(workId).orElse(null);

		if (volunteer != null && work != null) {
			KeepWorkBean keepWork = keepWorkRepository.findById(new KeepWorkId(volunteerId, workId)).orElse(null);
			if (keepWork != null) {
				keepWorkRepository.delete(keepWork);
			}
		}
	}

}