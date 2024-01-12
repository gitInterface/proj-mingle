package com.ispan.mingle.projmingle.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/hello")
    @SendTo("/topic/greeting")
    public void testRecieved() {

    }

}
