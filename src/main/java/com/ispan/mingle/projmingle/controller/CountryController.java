package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.CountryService;
import com.ispan.mingle.projmingle.domain.CountryBean;

@RestController
@RequestMapping("/api/country")
@CrossOrigin
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/getCountries")
    public ResponseEntity<?> getAllCountries() {
        List<CountryBean> countries = countryService.getAllCountries();
        System.err.println("countries = " + countries);
        if (countries == null || countries.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(countries);
        }
    }
}
