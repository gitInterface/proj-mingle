package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.WorktypeService;
import com.ispan.mingle.projmingle.domain.WorktypeBean;


@RestController
@RequestMapping("/api/worktype")
@CrossOrigin
public class WorktypeController {

    @Autowired
    private WorktypeService worktypeService;

    @GetMapping("/getWorktypes")
    public List<WorktypeBean> getAllWorktypes() {
        return worktypeService.getAllWorktypes();
    }
}