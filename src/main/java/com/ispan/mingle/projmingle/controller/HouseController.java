package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.HouseService;
import com.ispan.mingle.projmingle.domain.HouseBean;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/house")
@CrossOrigin
public class HouseController {
    @Autowired
    private HouseService houseService;

    @GetMapping("/findAll")
    public ResponseEntity<List<HouseBean>> findAll() {
        JSONObject obj = new JSONObject()
            .put("start", 0)
            .put("rows", 100);

        System.out.println(obj.toString());
        List<HouseBean> beans = houseService.find(obj.toString());
        System.out.println(beans);
        return ResponseEntity.ok().body(beans);
    }
    
    @GetMapping("/{id}")
    public HouseBean getMethodName(@PathVariable("id") Integer id) {
        return houseService.findById(id);
    }
    

}
