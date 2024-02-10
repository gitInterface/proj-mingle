package com.ispan.mingle.projmingle.repository;

import com.ispan.mingle.projmingle.dto.LandlordOrderDTO;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.ReviewBean;

import java.util.List;

@Repository
public interface LandlordRepository extends JpaRepository<LandlordBean, Integer> {

        LandlordBean findByUserid(String userid);

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
                        "WHERE l.landlordid = :landlordId order by r.reviewid desc")
        public List<ReviewBean> findReviewByLandlordId(@Param("landlordId") Integer landlordId);

        @Query("SELECT lb.landlordid FROM LandlordBean lb WHERE lb.userid = :id")
        Integer findByUserIDtoLordID(String id);

        @Query("SELECT new com.ispan.mingle.projmingle.dto.LandlordOrderDTO(w.name, h.name, o) " +
                        "FROM OrderBean o " +
                        "JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
                        "JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
                        "JOIN WorkBean w ON w.workid = wh.workid " +
                        "JOIN LandlordBean l ON w.landlordid = l.landlordid " +
                        "JOIN HouseBean h ON h.houseid = wh.houseid " +
                        "WHERE l.landlordid = :id order by o.orderid desc")
        List<LandlordOrderDTO> findAllOrderByLandlordID(Integer id);

        @Modifying
        @Query("UPDATE OrderBean o " +
                        "SET o.status = :status, o.isCancelled = :cancelled " +
                        "WHERE o.orderid = :id ")
        void setOrderStatus(Integer id, String status, boolean cancelled);


    @Query("SELECT new com.ispan.mingle.projmingle.dto.LandlordOrderDTO(w.name, h.name, o) " +
            "FROM OrderBean o " +
            "JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
            "JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
            "JOIN WorkBean w ON w.workid = wh.workid " +
            "JOIN LandlordBean l ON w.landlordid = l.landlordid " +
            "JOIN HouseBean h ON h.houseid = wh.houseid " +
            "WHERE o.orderid = :id")
    List<LandlordOrderDTO> findByOrderid(Integer id);

}

