package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.WorkTypeService;
import com.ispan.mingle.projmingle.domain.WorkTypeBean;


@RestController
@RequestMapping("/api/worktype")
@CrossOrigin
public class WorkTypeController {

    @Autowired
    private WorkTypeService workTypeService;

    @GetMapping("/getWorktypes")
    public List<WorkTypeBean> getAllWorkTypes() {
        return workTypeService.getAllWorkTypes();
    }
}