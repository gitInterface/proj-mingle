package com.ispan.mingle.projmingle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.VolunteerDetailService;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;

@RestController
@RequestMapping("/api/volunteerDetail")
@CrossOrigin
public class VolunteerDetailController {

    @Autowired
    private VolunteerDetailService volunteerDetailService;

    @GetMapping("/{id}")
    public VolunteerDetailBean getVolunteerDetail(@PathVariable String id) {
        return volunteerDetailService.findById(id);
    }

}
