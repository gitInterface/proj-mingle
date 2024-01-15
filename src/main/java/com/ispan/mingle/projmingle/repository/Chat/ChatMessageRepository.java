package com.ispan.mingle.projmingle.repository.Chat;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.MessageBean;

public interface ChatMessageRepository extends JpaRepository<MessageBean, Integer>, ChatMessageSpringDataJpaDAO {

}
