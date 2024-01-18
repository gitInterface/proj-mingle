package com.ispan.mingle.projmingle.controller.chat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ispan.mingle.projmingle.Service.chat.ChatMessageService;
import com.ispan.mingle.projmingle.domain.MessageBean;
import com.ispan.mingle.projmingle.repository.Chat.ChatNotification;

import lombok.RequiredArgsConstructor;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService service;

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageBean message) {
        MessageBean saveMsg = service.save(message);
        messagingTemplate.convertAndSendToUser(
                message.getRecieverID(), "/queue/messages",
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

        // 分別拿雙方發送給對方的對話紀錄
        List<MessageBean> sentMessages = service.findChatMessages(senderid, recieverid);
        List<MessageBean> recievedMessages = service.findChatMessages(recieverid, senderid);

        // 合併
        List<MessageBean> allMessages = new ArrayList<MessageBean>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(recievedMessages);

        // 排序
        allMessages.sort(Comparator.comparing(MessageBean::getCreatedTime));
        return ResponseEntity.ok(allMessages);
    }

}
