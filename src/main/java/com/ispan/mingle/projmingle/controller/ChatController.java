package com.ispan.mingle.projmingle.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ispan.mingle.projmingle.Service.ChatMessageService;
import com.ispan.mingle.projmingle.domain.MessageBean;
import com.ispan.mingle.projmingle.repository.Chat.ChatNotification;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService service;

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageBean message) {
        MessageBean saveMsg = service.save(message);
        messagingTemplate.convertAndSendToUser(
                message.getRecieverID(), "/quene/messages",
                ChatNotification.builder()
                        .chatid(saveMsg.getChatid())
                        .senderID(saveMsg.getSenderID())
                        .recieverID(saveMsg.getRecieverID())
                        .contents(saveMsg.getContents())
                        .build());
    }

    @GetMapping("/messages/{senderid}/{recieverid}")
    public ResponseEntity<List<MessageBean>> findChatMessages(@PathVariable String senderid,
            @PathVariable String recieverid) {
        return ResponseEntity.ok(service.findChatMessages(senderid, recieverid));
    }

}
