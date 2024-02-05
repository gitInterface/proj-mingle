package com.ispan.mingle.projmingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.CountryBean;

public interface CountryRepository extends JpaRepository<CountryBean, String> {

}
