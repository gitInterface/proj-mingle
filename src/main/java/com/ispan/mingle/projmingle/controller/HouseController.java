package com.ispan.mingle.projmingle.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/findAllHousesWithPhotos")
    public ResponseEntity<List<HouseBean>> findAllHousesWithPhotos() {
        List<HouseBean> houses = houseService.findAllHousesWithPhotos();
        return ResponseEntity.ok(houses);
    }

    @GetMapping("/find")
    public ResponseEntity<HouseBean> findById(@RequestParam Integer houseId) {
        Optional<HouseBean> house = houseService.findById(houseId);
        return house.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/modify/{houseid}")
    public ResponseEntity<?> modify(
            @PathVariable(name = "houseid") Integer houseid, @RequestBody String entity) {
        if (houseid == null || !houseService.exists(houseid)) {
            ResponseEntity<Void> response = ResponseEntity.notFound().build();
            return response;
        } else {
            HouseBean result = houseService.modify(entity);
            if (result == null) {
                return ResponseEntity.notFound().build();
            } else {
                ResponseEntity<HouseBean> response = ResponseEntity.ok().body(result);
                return response;
            }
        }
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

    @PostMapping("/addHouse")
    public void getHouse(@RequestBody HouseBean bean, @RequestHeader("sessionToken")String session)  {
        houseService.setNewHouse(bean,session);
    }

}
