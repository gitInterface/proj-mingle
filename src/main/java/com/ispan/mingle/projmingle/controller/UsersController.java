package com.ispan.mingle.projmingle.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.dto.LoginUserDTO;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class UsersController {

    // 拿到 session 裡面的東西，然後傳到前端去
    @GetMapping("/api/users/map")
    public ResponseEntity<?> testSessionValue(HttpSession httpSession){
    
       System.out.println("檢查登入 controller");

       LoginUserDTO loginUser = (LoginUserDTO) httpSession.getAttribute("loginUser");

       if(loginUser == null){
           System.out.println("session attribute 空的");
           return new ResponseEntity<String>("session attribute null", HttpStatus.UNAUTHORIZED); // 401
       }

       Map<String,String> responseMap =  new HashMap<>();

       responseMap.put("userId",loginUser.getUserId());
       responseMap.put("userName",loginUser.getUsername());

       return new ResponseEntity<Map<String,String>>(responseMap, HttpStatus.OK);   
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<?> usersLogout(HttpSession httpSession){

        System.out.println("登出 controller");

        httpSession.removeAttribute("loginUser");

        return new ResponseEntity<String>("登出ok", HttpStatusCode.valueOf(200));
    }

}
