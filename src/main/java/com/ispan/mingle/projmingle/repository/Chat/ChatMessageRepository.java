package com.ispan.mingle.projmingle.repository.Chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.mingle.projmingle.domain.MessageBean;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<MessageBean, Integer>, ChatMessageJpaDAO {
    List<MessageBean> findByChatid(String chatid);

    @Query("SELECT m.senderID, m.recieverID, v.name, m.contents, m.createdTime, m.chatid FROM MessageBean m JOIN VolunteerDetailBean v ON m.senderID = v.userid WHERE m.chatid IN (SELECT DISTINCT cr.chatid FROM ChatRoomBean cr WHERE cr.senderid = :userid OR cr.recieverid = :userid) AND m.createdTime = (SELECT MAX(innerMsg.createdTime) FROM MessageBean innerMsg WHERE innerMsg.chatid = m.chatid) ORDER BY m.createdTime DESC")
    List<Object[]> findLatestMessagesForUser(@Param("userid") String userid);
}
