package com.ispan.mingle.projmingle.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ispan.mingle.projmingle.Service.chat.ChatMessageService;
import com.ispan.mingle.projmingle.dto.ChatPreviewDTO;
import com.ispan.mingle.projmingle.repository.Chat.ChatMessageRepository;

@SpringBootTest
public class ChatMessageServiceTests {
    @Autowired
    private ChatMessageRepository repository;
    @Autowired
    private ChatMessageService service;

    @Test
    public void testFindAllChatPreviews() {
        // List<Object[]> latestMessagesForUser =
        // repository.findLatestMessagesForUser("1@gmail.com");
        // System.err.println(latestMessagesForUser);
        List<ChatPreviewDTO> allChatPreviews = service.findAllChatPreviews("1@gmail.com");
        System.err.println(allChatPreviews);
        // System.err.println("你好");
        // System.err.println("你好");
        // System.err.println("你好");
    }
}
