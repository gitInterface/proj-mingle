package com.ispan.mingle.projmingle.repository.Chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;

public interface ChatUserRepository extends JpaRepository<VolunteerDetailBean, String>, ChatUserSpringDataJpaDAO {

    List<VolunteerDetailBean> findAllByStatus(Character online);

}