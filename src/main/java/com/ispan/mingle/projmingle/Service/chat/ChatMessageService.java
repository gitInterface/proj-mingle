package com.ispan.mingle.projmingle.Service.chat;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.mingle.projmingle.domain.MessageBean;
import com.ispan.mingle.projmingle.dto.ChatPreviewDTO;
import com.ispan.mingle.projmingle.repository.Chat.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;
    private final ChatPreviewDTO dto;

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

    public List<ChatPreviewDTO> findAllChatPreviews(String senderid) {
        List<Object[]> result = repository.findLatestMessagesForUser(senderid);

        return result.stream()
                .map((Object[] row) -> ChatPreviewDTO.builder()
                        .userid((String) row[0])
                        .username((String) row[1])
                        .photo(Base64.getEncoder().encodeToString((byte[]) row[2]))
                        .contents((String) row[3])
                        .createdTime((Date) row[4])
                        .build())
                .collect(Collectors.toList());
    }
}
