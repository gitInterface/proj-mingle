package com.ispan.mingle.projmingle.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.ChatRoomBean;
import com.ispan.mingle.projmingle.repository.Chat.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository repository;

    public Optional<String> getChatRoomId(String senderid, String recieverid, boolean createNewRoomIfNotExists) {
        return repository.findBySenderidAndRecieverid(senderid, recieverid)
                .map(ChatRoomBean::getRoomid)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        var roomid = createChat(senderid, recieverid);
                    }
                    return Optional.empty();
                });
    }

    private String createChat(String senderid, String recieverid) {
        var roomid = String.format("%s_%s", senderid, recieverid);
        ChatRoomBean senderReciever = ChatRoomBean.builder()
                .roomid(roomid)
                .senderid(senderid)
                .recieverid(recieverid)
                .build();
        ChatRoomBean recieverSender = ChatRoomBean.builder()
                .roomid(roomid)
                .senderid(recieverid)
                .recieverid(senderid)
                .build();
        repository.save(senderReciever);
        repository.save(recieverSender);
        return roomid;
    }
}
