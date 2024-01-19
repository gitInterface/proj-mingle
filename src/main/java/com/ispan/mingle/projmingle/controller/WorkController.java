package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.repository.WorkRepository;

@RestController
@CrossOrigin
public class WorkController {

    @Autowired
    private WorkRepository workRepository;

    @GetMapping("/works")
    public List<WorkBean> getAllWorks() {
        return workRepository.findAll();
    }
}