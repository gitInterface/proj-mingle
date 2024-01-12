package com.ispan.mingle.projmingle.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.ispan.mingle.projmingle.Service.VolunteerService;
import com.ispan.mingle.projmingle.domain.VolunteerBean;

import jakarta.servlet.http.HttpSession;

public class LoginController {
    @Autowired
	private MessageSource messageSource;
	
	@Autowired
	private VolunteerService volunteerService;

	@PostMapping(path = {"/api/login.controller"})
	public String handlerMethod(Locale locale, Model model,
			String username, String password, HttpSession session) {
        //接收使用者輸入的資料
        //驗證使用者輸入的資料
		Map<String, String> errorMsgs = new HashMap<>();
		model.addAttribute("errors", errorMsgs);
		
		if(username==null || username.length()==0) {
			errorMsgs.put("xxx1", messageSource.getMessage("login.required.id", null, locale));
		}
		if(password==null || password.length()==0) {
			errorMsgs.put("xxx2", messageSource.getMessage("login.required.pwd", null, locale));
		}
		
		if(errorMsgs!=null && !errorMsgs.isEmpty()) {
			return "/secure/login";
		}
		

//呼叫企業邏輯程式
		VolunteerBean bean = volunteerService.login(username, password);
		
//根據執行結果呼叫View
		if(bean==null) {
			errorMsgs.put("xxx2", messageSource.getMessage("login.failed", null, locale));
			return "/secure/login";
		} else {
			session.setAttribute("user", bean);
			return "redirect:/";
		}
	}
}
