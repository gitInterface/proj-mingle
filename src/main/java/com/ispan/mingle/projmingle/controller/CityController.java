package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.CityService;
import com.ispan.mingle.projmingle.domain.CityBean;


@RestController
@RequestMapping("/api/city")
@CrossOrigin
public class CityController {

    @Autowired
    private CityService cityService;
    
    @GetMapping("/getCities")
    public List<CityBean> getAllCities() {
        return cityService.getAllCities();
    }
}
