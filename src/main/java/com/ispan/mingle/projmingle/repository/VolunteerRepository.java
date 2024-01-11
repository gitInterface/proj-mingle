package com.ispan.mingle.projmingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.VolunteerBean;

public interface VolunteerRepository extends JpaRepository<VolunteerBean, String>, VolunteerSpringDataJpaDAO{

    
}