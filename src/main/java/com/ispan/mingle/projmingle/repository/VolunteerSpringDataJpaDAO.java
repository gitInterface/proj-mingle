package com.ispan.mingle.projmingle.repository;

import org.springframework.data.jpa.repository.Query;

import com.ispan.mingle.projmingle.domain.VolunteerBean;

public interface VolunteerSpringDataJpaDAO {
    public abstract VolunteerBean select(String username);



}