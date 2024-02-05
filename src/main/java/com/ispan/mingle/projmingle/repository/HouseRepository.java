package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.mingle.projmingle.domain.HouseBean;

public interface HouseRepository extends JpaRepository<HouseBean, Integer>, HouseSpringDataJpaDAO {
    // lordid找所有房子 (只找出沒被刪的，並且床大於0)
    @Query("SELECT h FROM HouseBean h WHERE h.lordid = :lordid AND h.isDeleted = '0' AND h.beds > 0")
    List<HouseBean> findHousesWithNonDeletedAndExistBeds(@Param("lordid") Integer lordid);

    @Query("UPADATE HouseBean h SET h.beds = :beds WHERE h.houseid = :houseid")
    public HouseBean updateBeds(@Param("beds") Integer beds, @Param("houseid") Integer houseid);
}
