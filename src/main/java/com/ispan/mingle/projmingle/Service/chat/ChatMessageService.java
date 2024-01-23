package com.ispan.mingle.projmingle.Service.chat;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.mingle.projmingle.domain.MessageBean;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.dto.ChatPreviewDTO;
import com.ispan.mingle.projmingle.repository.VolunteerDetailRepository;
import com.ispan.mingle.projmingle.repository.Chat.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final VolunteerDetailRepository detailRepository;
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
                .map((Object[] row) -> {
                    ChatPreviewDTO.ChatPreviewDTOBuilder builder = ChatPreviewDTO.builder()
                            .senderid((String) row[0])
                            .recieverid((String) row[1])
                            .sendername((String) row[2])
                            .recievername(detailRepository.findById((String) row[1]).get().getName())
                            .contents((String) row[4])
                            .createdTime((Date) row[5]);
                    if (row[3] != null) {
                        builder.photo(Base64.getEncoder().encodeToString((byte[]) row[3]));
                    }
                    return builder.build();
                })
                .collect(Collectors.toList());
    }

    public List<VolunteerDetailBean> findAllUser(String senderid) {
        return detailRepository.findAllByUseridIsNot(senderid);

    }
}
