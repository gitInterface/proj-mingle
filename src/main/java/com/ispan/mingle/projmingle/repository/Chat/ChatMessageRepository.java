package com.ispan.mingle.projmingle.repository.Chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.mingle.projmingle.domain.MessageBean;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<MessageBean, Integer>, ChatMessageJpaDAO {
    List<MessageBean> findByChatid(String chatid);

    @Query("SELECT m.senderID, v.name, v.image, m.contents, m.createdTime FROM MessageBean m JOIN VolunteerDetailBean v ON m.senderID = v.userid WHERE (m.senderID = :userid OR m.recieverID = :userid) GROUP BY m.senderID, v.name, v.image, m.contents, m.createdTime ORDER BY MAX(m.createdTime) DESC")
    List<Object[]> findLatestMessagesForUser(@Param("userid") String userid);

}
