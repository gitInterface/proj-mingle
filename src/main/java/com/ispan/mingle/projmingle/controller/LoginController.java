package com.ispan.mingle.projmingle.controller;

import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.VolunteerService;
import com.ispan.mingle.projmingle.domain.VolunteerBean;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class LoginController {
	
	@Autowired
	private VolunteerService volunteerService;

	@PostMapping("/login.controller")
	@ResponseBody
	public String login(HttpSession session, @RequestBody String json) {
		JSONObject responseJson = new JSONObject();
		// 接收及驗證使用者資料
		JSONObject obj = new JSONObject(json);
		String userid = obj.isNull("userid") ? null : obj.getString("userid");
		String password = obj.isNull("password") ? null : obj.getString("password");
		// 呼叫企業邏輯
		VolunteerBean bean = volunteerService.login(userid, password);
		// 根據結果呼叫View
		if(bean==null) {
			responseJson.put("message", "登入失敗");
			responseJson.put("success", false);
		} else {
			String sessionToken = generateSessionToken(userid);
			session.setAttribute("sessionToken", sessionToken);

			session.setAttribute("user", bean);
			responseJson.put("message", "登入成功");
			responseJson.put("success", true);
			responseJson.put("sessionToken", sessionToken);
		}
		return responseJson.toString();
	}

	public String generateSessionToken(String userid) {
		// UUID.randomUUID() 產生32個字的亂數字碼
		// 再加上userid
		return UUID.randomUUID().toString().replace("-", "")+userid;
	}
}
