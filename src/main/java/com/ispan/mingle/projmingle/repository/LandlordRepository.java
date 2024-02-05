package com.ispan.mingle.projmingle.repository;
    
    


import java.lang.Integer;
import com.ispan.mingle.projmingle.domain.LandlordBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * 持久层
 *
 * @author makejava
 * @since 2024-01-30 10:44:24
 */
public interface LandlordRepository extends JpaRepository<LandlordBean, Integer> {
    @Query("SELECT lb.landlordid FROM LandlordBean lb WHERE lb.userid = :id")
    Integer findByUserIDtoLordID(String id);
}

