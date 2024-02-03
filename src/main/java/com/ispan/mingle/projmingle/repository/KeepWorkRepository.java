package com.ispan.mingle.projmingle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ispan.mingle.projmingle.domain.KeepWorkBean;
import com.ispan.mingle.projmingle.domain.KeepWorkId;
import com.ispan.mingle.projmingle.domain.VolunteerBean;
import com.ispan.mingle.projmingle.domain.WorkBean;

@Repository
public interface KeepWorkRepository extends JpaRepository<KeepWorkBean, KeepWorkId> {
    Optional<KeepWorkBean> findByVolunteerAndWork(VolunteerBean volunteer, WorkBean work);
    boolean existsByVolunteer_UseridAndWork_Workid(String volunteerId, Integer workId);
    List<KeepWorkBean> findByVolunteer(VolunteerBean volunteer);
}