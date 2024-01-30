package com.ispan.mingle.projmingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.domain.VolunteerBean;

@Repository
public interface VolunteerRepository extends JpaRepository<VolunteerBean, String>, VolunteerSpringDataJpaDAO{

    /**透過帳號取得房東id */
    @Query("SELECT l FROM VolunteerBean v JOIN LandlordBean l ON v.userid = l.userid WHERE v.userid = :userid")
    public LandlordBean findLandlordByUserid(@Param("userid") String userid);

}