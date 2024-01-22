package com.ispan.mingle.projmingle.Service.chat;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.mingle.projmingle.domain.ChatRoomBean;
import com.ispan.mingle.projmingle.repository.Chat.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository repository;

    public Optional<String> getChatId(String senderid, String recieverid, boolean createNewRoomIfNotExists) {
        return repository.findBySenderidAndRecieverid(senderid, recieverid)
                .map(ChatRoomBean::getChatid)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        var chatid = createChat(senderid, recieverid);
                        return Optional.of(chatid);
                    }
                    return Optional.empty();
                });
    }

    private String createChat(String senderid, String recieverid) {
        var chatid = String.format("%s_%s", senderid, recieverid);
        ChatRoomBean senderReciever = ChatRoomBean.builder()
                .chatid(chatid)
                .senderid(senderid)
                .recieverid(recieverid)
                .build();
        ChatRoomBean recieverSender = ChatRoomBean.builder()
                .chatid(chatid)
                .senderid(recieverid)
                .recieverid(senderid)
                .build();
        repository.save(senderReciever);
        repository.save(recieverSender);
        return chatid;
    }
}
