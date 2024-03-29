package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.CityBean;

public interface CityRepository extends JpaRepository<CityBean, String> {
    List<CityBean> findByArea(String area);
}
