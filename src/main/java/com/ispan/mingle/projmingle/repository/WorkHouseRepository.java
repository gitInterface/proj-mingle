package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.mingle.projmingle.domain.WorkHouseBean;

public interface WorkHouseRepository  extends JpaRepository<WorkHouseBean, Integer> {
    
    @Query("SELECT wh FROM WorkHouseBean wh WHERE wh.houseid = :houseId")
    public List<WorkHouseBean> findAllByHouseId(Integer houseId);

}
