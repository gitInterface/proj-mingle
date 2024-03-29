package com.ispan.mingle.projmingle.Service;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
	private VolunteerDetailService volunteerDetailService;
	@Autowired
	private WorkRepository workRepository;

	/*
	 * CREATE
	 */
	// 新建用戶
	public VolunteerBean create(String username, String password) {
		try {
			if (username != null) {
				VolunteerBean insert = new VolunteerBean();
				Integer maxUserID = volunteerRepository.findMaxUserID();
				insert.setUserid(Integer.toString(maxUserID != null ? maxUserID + 1 : 1));
				insert.setUsername(username);
				insert.setPassword(password);
				insert.setIsAdmin(false);
				VolunteerBean newMember = volunteerRepository.save(insert);
				// 新增用戶的詳細資料列表
				volunteerDetailService.createById(insert.getUserid());
				return newMember;
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
	public VolunteerBean login(String username, String password) {
		VolunteerBean user = volunteerRepository.findByUsername(username);
		if (user != null) {
			if (password != null) {
				String pass = user.getPassword(); // 資料庫取出
				// 使用者輸入(password)
				if (pass.equals(password)) {
					return user;
				}
			}
		}
		return null;
	}

	public boolean existsByUsername(String username) {
		if (username != null) {
			return volunteerRepository.existsByUsername(username);
		}
		return false;
	}

	// 查詢管理者數量
	public Integer getAdminCount() {
		return volunteerRepository.findAll().stream().map(VolunteerBean::getIsAdmin).mapToInt(isAdmin -> isAdmin ? 1 : 0)
				.sum();
	}

	// 獲取所有使用者(管理者介面用)
	public Page<VolunteerBean> getAllVolunteers(Pageable pageable, String keyword, Boolean isAdmin) {
		Specification<VolunteerBean> spec = Specification.where(null);
		if (isAdmin != null) {
			spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isAdmin"), isAdmin));
		}
		if (keyword != null) {
			spec = spec.and(
					(root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + keyword + "%"));
		}
		return volunteerRepository.findAll(spec, pageable);
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

	public void resetPassword(String userId, String newPassword) {
		// Retrieve the volunteer by userId and update the password
		Optional<VolunteerBean> optionalVolunteer = volunteerRepository.findById(userId);
		if (optionalVolunteer.isPresent()) {
			VolunteerBean volunteer = optionalVolunteer.get();
			volunteer.setPassword(newPassword);
			volunteerRepository.save(volunteer);
		} else {
			// Handle case where volunteer is not found
		}
	}

	public VolunteerBean save(VolunteerBean volunteer) {
		return volunteerRepository.save(volunteer);
	}

	public VolunteerBean loginByID(String userId, String password) {
		if (userId != null && password != null) {
			VolunteerBean user = volunteerRepository.findByUserid(userId);
			if (user != null && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	public VolunteerBean updatePasswordById(String json) {
		JSONObject job = new JSONObject(json);
		String id = job.isNull("id") ? null : job.getString("id");
		String password = job.isNull("password") ? null : job.getString("password");
		if (id != null && id != "" && id != "undefined" && password != null && password != ""
				&& password != "undefined") {
			VolunteerBean volunteer = volunteerRepository.findByUserid(id);
			volunteer.setPassword(password);
			return save(volunteer);
		} else {
			return null;
		}
	}
}