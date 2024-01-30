package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.ReviewBean;

@Repository
public interface LandlordRepository extends JpaRepository<LandlordBean, Integer> {

    /** 透過房東id查詢報名訂單 */
    @Query("SELECT o FROM OrderBean o " +
            "JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
            "JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
            "JOIN WorkBean w ON w.workid = wh.workid " +
            "JOIN LandlordBean l ON w.landlordid = l.landlordid " +
            "WHERE l.landlordid = :landlordId")
    public List<OrderBean> findOrderByLandlordId(@Param("landlordId") Integer landlordId);

    /** 透過房東id查詢評價 */
    @Query("SELECT r FROM OrderBean o " +
            "JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
            "JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
            "JOIN WorkBean w ON w.workid = wh.workid " +
            "JOIN LandlordBean l ON w.landlordid = l.landlordid " +
            "JOIN ReviewBean r ON r.orderid = o.orderid " +
            "WHERE l.landlordid = :landlordId")
    public List<ReviewBean> findReviewByLandlordId(@Param("landlordId") Integer landlordId);


    
}
