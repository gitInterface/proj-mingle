package com.ispan.mingle.projmingle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ispan.mingle.projmingle.Service.VolunteerService;

public class RegisterController {
    @Autowired
	private VolunteerService volunteerService;


}
