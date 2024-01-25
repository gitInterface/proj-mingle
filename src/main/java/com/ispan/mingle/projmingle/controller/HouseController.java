package com.ispan.mingle.projmingle.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.HouseService;
import com.ispan.mingle.projmingle.domain.HouseBean;


@RestController
@RequestMapping("/api/house")
@CrossOrigin
public class HouseController {
    @Autowired
    private HouseService houseService;

    @GetMapping("/findAll")
    public ResponseEntity<List<HouseBean>> getAllHouses() {
        List<HouseBean> houses = houseService.findAllHouses();
        return ResponseEntity.ok(houses);
    }

    @GetMapping("/find")
    public ResponseEntity<HouseBean> findById(@RequestParam Integer houseId) {
        Optional<HouseBean> house = houseService.findById(houseId);
        return house.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<HouseBean> save(@RequestBody HouseBean house) {
        HouseBean savedHouse = houseService.save(house);
        return ResponseEntity.ok(savedHouse);
    }

    @DeleteMapping("/delete/{houseId}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer houseId) {
        try {
            houseService.deleteById(houseId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Log the exception or print the stack trace
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
