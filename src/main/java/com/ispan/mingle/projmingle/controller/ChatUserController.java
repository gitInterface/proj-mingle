package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ispan.mingle.projmingle.Service.ChatUserService;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatUserController {
    private final ChatUserService service;

    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public VolunteerDetailBean addUser(@Payload VolunteerDetailBean user) {
        service.saveUser(user);
        return user;
    }

    @MessageMapping("/user/disconnectUser")
    @SendTo("/user/topic")
    public VolunteerDetailBean disconnect(@Payload VolunteerDetailBean user) {
        service.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<VolunteerDetailBean>> findConnectedUsers() {
        return ResponseEntity.ok(service.findConnectedUsers());
    }

}
