package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;

public interface VolunteerDetailRepository extends CrudRepository<VolunteerDetailBean, String> {

    List<VolunteerDetailBean> findAllByUseridIsNot(String userId);

}
