package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.ReviewBean;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.dto.LandlordOrderDTO;

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

        /** 透過房東id查詢工作 */
        @Query("SELECT w FROM OrderBean o " +
                        "JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
                        "JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
                        "JOIN WorkBean w ON w.workid = wh.workid " +
                        "WHERE w.landlordid = :landlordId")
        public List<WorkBean> findWorkByLandlordId(@Param("landlordId") Integer landlordId);

        /** 透過房東id查詢房東擁有的訂單數 */
        @Query("SELECT COUNT(o) FROM OrderBean o " +
                        "JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
                        "JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
                        "JOIN WorkBean w ON w.workid = wh.workid " +
                        "WHERE w.landlordid = :landlordId")
        public Integer findTotalNumberByLandlordId(@Param("landlordId") Integer landlordId);

        /** 透過房東id查詢房東擁有的訂單報名人數 */
        @Query("SELECT SUM(o.numbers) FROM OrderBean o " +
                        "JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
                        "JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
                        "JOIN WorkBean w ON w.workid = wh.workid " +
                        "WHERE w.landlordid = :landlordId")
        public Integer findOrderNumberByLandlordId(@Param("landlordId") Integer landlordId);

        /** 透過房東id查詢房東每年每月訂單數 */
        @Query("SELECT YEAR(o.createdAt) AS order_year, " +
                        "MONTH(o.createdAt) AS order_month, " +
                        "COUNT(o) AS monthly_order_count " +
                        "FROM OrderBean o " +
                        "JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
                        "JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
                        "JOIN WorkBean w ON w.workid = wh.workid " +
                        "WHERE w.landlordid = :landlordId " +
                        "GROUP BY YEAR(o.createdAt), MONTH(o.createdAt)")
        public List<Object[]> findMonthlyOrderCountByLandlordId(@Param("landlordId") Integer landlordId);

        /** 透過房東id查詢房東每個工作訂單數 */

        @Query("SELECT COUNT(o), w.workid, w.name ,w.isDeleted " +
                        "FROM OrderBean o " +
                        "full JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
                        "full JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
                        "full JOIN WorkBean w ON w.workid = wh.workid " +
                        "WHERE w.landlordid = :landlordId " +
                        "GROUP BY w.workid, w.name, w.isDeleted")
        public List<Object[]> findWorkOrderCountByLandlordId(@Param("landlordId") Integer landlordId);

        /** 透過房東id查詢所有工作瀏覽量總和 */

        @Query("SELECT SUM(w.views) FROM OrderBean o " +
                        "full JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
                        "full JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
                        "full JOIN WorkBean w ON w.workid = wh.workid " +
                        "WHERE w.landlordid = :landlordId")
        public Integer findTotalViewsByLandlordId(@Param("landlordId") Integer landlordId);

        /** 透過房東id查詢每個工作的瀏覽量 */
        @Query("SELECT w.workid ,w.views, w.name, w.isDeleted FROM OrderBean o " +
                        "full JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
                        "full JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
                        "full JOIN WorkBean w ON w.workid = wh.workid " +
                        "WHERE w.landlordid = :landlordId GROUP BY w.workid ,w.views, w.name,w.isDeleted")
        public List<Object[]> findWorkViewsByLandlordId(@Param("landlordId") Integer landlordId);

        /** 透過房東id查詢對房東的平均評價分數 */

        @Query("SELECT AVG(r.stars)  FROM OrderBean o " +
                        "full JOIN ReviewBean r ON r.orderid = o.orderid " +
                        "full JOIN OrderWorkHouseBean owh ON owh.orderid = o.orderid " +
                        "full JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
                        "full JOIN WorkBean w ON w.workid = wh.workid " +
                        "WHERE w.landlordid = :landlordId")
        public Double findAverageStarsByLandlordId(@Param("landlordId") Integer landlordId);

        /** 房東所有工作總評價數 */
        @Query("SELECT count(r.reviewid)  FROM OrderBean o " +
                        "full JOIN ReviewBean r ON r.orderid = o.orderid " +
                        "full JOIN OrderWorkHouseBean owh ON owh.orderid = o.orderid " +
                        "full JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
                        "full JOIN WorkBean w ON w.workid = wh.workid " +
                        "WHERE w.landlordid = :landlordId")
        public Integer findCountReviewByLandlordId(@Param("landlordId") Integer landlordId);

        /** 房東每個工作分別評價數及評分 */
        @Query("SELECT w.workid, count(r.reviewid), AVG(r.stars)  FROM OrderBean o " +
                        "full JOIN ReviewBean r ON r.orderid = o.orderid " +
                        "full JOIN OrderWorkHouseBean owh ON owh.orderid = o.orderid " +
                        "full JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
                        "full JOIN WorkBean w ON w.workid = wh.workid " +
                        "WHERE w.landlordid = :landlordId GROUP BY w.workid")
        public List<Object[]> findWorkReviewByLandlordId(@Param("landlordId") Integer landlordId);

}
