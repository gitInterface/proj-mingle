package com.ispan.mingle.projmingle.Service.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.MessageBean;
import com.ispan.mingle.projmingle.repository.Chat.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public MessageBean save(MessageBean message) {
        var chatid = chatRoomService.getChatId(
                message.getSenderID(),
                message.getRecieverID(),
                true)
                .orElseThrow();
        message.setChatid(chatid);
        repository.save(message);
        return message;
    }

    public List<MessageBean> findChatMessages(String senderid, String recieverid) {
        var chatid = chatRoomService.getChatId(senderid, recieverid, false);
        return chatid.map(repository::findByChatid).orElse(new ArrayList<>());
    }
}
