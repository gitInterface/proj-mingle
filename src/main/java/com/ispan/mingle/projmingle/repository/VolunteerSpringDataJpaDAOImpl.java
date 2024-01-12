package com.ispan.mingle.projmingle.repository;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ispan.mingle.projmingle.domain.VolunteerBean;

import jakarta.persistence.PersistenceContext;

@Repository
public class VolunteerSpringDataJpaDAOImpl implements VolunteerSpringDataJpaDAO{

  @PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}

    @Override
	public VolunteerBean select(String custid) {
		if(custid!=null) {
			return this.getSession().get(VolunteerBean.class, custid);
		}
		return null;
	}

    
}
