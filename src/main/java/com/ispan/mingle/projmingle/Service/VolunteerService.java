package com.ispan.mingle.projmingle.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.VolunteerBean;
import com.ispan.mingle.projmingle.repository.VolunteerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VolunteerService {
	@Autowired
	private VolunteerRepository volunteerRepository = null;

	@Autowired
    private JavaMailSender javaMailSender;

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
	
}