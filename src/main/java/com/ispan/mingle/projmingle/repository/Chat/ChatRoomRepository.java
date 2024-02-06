package com.ispan.mingle.projmingle.repository.Chat;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.ChatRoomBean;

public interface ChatRoomRepository extends JpaRepository<ChatRoomBean, Integer> {

    Optional<ChatRoomBean> findBySenderidAndRecieverid(String senderid, String recieverid);

}
