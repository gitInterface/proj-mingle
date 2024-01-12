package com.ispan.mingle.projmingle.Service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.ispan.mingle.projmingle.domain.VolunteerBean;
import com.ispan.mingle.projmingle.repository.VolunteerRepository;

public class VolunteerService {
    @Autowired
	private VolunteerRepository volunteerRepository = null;	
    
    public VolunteerBean login(String username, String password) {
		VolunteerBean select = volunteerRepository.select(username);
		if(select!=null) {
			if(password!=null) {
				String pass = select.getPassword();		//資料庫取出
				byte[] temp = password.getBytes();		//使用者輸入
				if(pass.equals(temp.toString()))     {
					return select;
				}
			}
		}
		return null;
	}
}