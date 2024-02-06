package com.ispan.mingle.projmingle.repository;

import com.ispan.mingle.projmingle.domain.VolunteerBean;

public interface VolunteerSpringDataJpaDAO {
    public abstract VolunteerBean select(String username);

}