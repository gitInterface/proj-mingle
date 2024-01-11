package com.ispan.mingle.projmingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.MessageBean;

public interface ChatRepository extends JpaRepository<MessageBean, Integer>, ChatSpringDataJpaDAO {

}
