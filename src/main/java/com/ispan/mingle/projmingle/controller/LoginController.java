package com.ispan.mingle.projmingle.controller;

import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.LandlordService;
import com.ispan.mingle.projmingle.Service.VolunteerService;
import com.ispan.mingle.projmingle.domain.VolunteerBean;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class LoginController {

	@Autowired
	private VolunteerService volunteerService;
	@Autowired
	private LandlordService landlordService;

	@PostMapping("/login.controller")
	@ResponseBody
	public String login(HttpSession session, @RequestBody String json) {
		JSONObject responseJson = new JSONObject();
		// 接收及驗證使用者資料
		JSONObject obj = new JSONObject(json);
		String username = obj.isNull("username") ? null : obj.getString("username");
		String password = obj.isNull("password") ? null : obj.getString("password");
		// 呼叫企業邏輯
		VolunteerBean bean = volunteerService.login(username, password);
		// 根據結果呼叫View
		if (bean == null) {
			responseJson.put("message", "登入失敗");
			responseJson.put("success", false);
		} else {
			String userid = bean.getUserid();
			String sessionToken = generateSessionToken(userid);
			// session.setAttribute("sessionToken", sessionToken);
			// session.setAttribute("user", bean);
			responseJson.put("message", "登入成功");
			responseJson.put("success", true);
			responseJson.put("sessionToken", sessionToken);
			responseJson.put("userID", userid);
			// put lordID給前端
			responseJson.put("lordID", landlordService.findByUserIDtoLordID(userid));
			responseJson.put("adminPermission", bean.getIsAdmin());
		}
		return responseJson.toString();
	}

	public String generateSessionToken(String userid) {
		// UUID.randomUUID() 產生32個字的亂數字碼
		return UUID.randomUUID().toString().replace("-", "") + userid;
	}
}
