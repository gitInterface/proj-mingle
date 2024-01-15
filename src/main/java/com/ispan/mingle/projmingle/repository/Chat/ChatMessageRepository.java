package com.ispan.mingle.projmingle.repository.Chat;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.MessageBean;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<MessageBean, Integer>, ChatMessageJpaDAO {
    List<MessageBean> findByChatid(String chatid);
}
