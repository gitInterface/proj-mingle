package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.mingle.projmingle.domain.WorkHouseBean;

public interface WorkHouseRepository extends JpaRepository<WorkHouseBean, Integer> {

    @Query("SELECT wh FROM WorkHouseBean wh WHERE wh.houseid = :houseId")
    public List<WorkHouseBean> findAllByHouseId(Integer houseId);

    // workid找出所有綁定的房子(排除解綁的)，回傳那些房子的id
    @Query("SELECT wh.houseid FROM WorkHouseBean wh WHERE wh.workid = :workid AND wh.isDeleted = false")
    List<Integer> findHouseIdsByWorkIdAndIsDeletedFalse(@Param("workid") Integer workid);

    // workid,houseid，找出是否存在這個組合(理論上回傳List或Optional。但由於update過程會排除，因此只可能存在唯一的bean或是null)
    public WorkHouseBean findByWorkidAndHouseid(Integer workid, Integer houseid);
}
