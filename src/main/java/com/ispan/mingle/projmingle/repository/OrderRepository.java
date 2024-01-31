package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;

public interface OrderRepository extends JpaRepository<OrderBean, Integer> {

    @Query("SELECT vd FROM OrderBean o " +
            "JOIN VolunteerBean v ON o.userid = v.userid " +
            "JOIN VolunteerDetailBean vd ON o.userid = vd.userid " +
            "WHERE o.orderid = :orderid")
    public VolunteerDetailBean findVolunteerDetailByOrderId(@Param("orderid") Integer orderid);

    /** 透過使用者id查詢Work旅行足跡 */

    // SELECT order.orderID, order.fk_userID, order.status, work.workID,
    // work.workName, work.description, work_photo.photoSize,
    // work_photo.contentType, work_photo.photo, work_photo.isDeleted
    // FROM [Order] AS order
    // JOIN Order_Work_House AS order_work_house ON order.orderID =
    // order_work_house.fk_orderID
    // JOIN Work_House AS work_house ON order_work_house.fk_ID = work_house.ID
    // JOIN Work AS work ON work_house.fk_workID = work.workID
    // JOIN work_photo ON work.workID = work_photo.fk_workID
    // WHERE order.status = '1' AND order.fk_userID = 'userID';
    @Query("SELECT order.orderID, order.fk_userID, order.status, work.workID," +
            "work.workName, work.description, work_photo.photoSize," +
            "work_photo.contentType, work_photo.photo, work_photo.isDeleted FROM OrderBean o " +
            "JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
            "JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
            "JOIN WorkBean w ON wh.workid = w.workid " +
            "JOIN WorkPhotoBean wp ON w.workid = wp.workid " +
            "WHERE o.status = :finshed" +
            "AND o.fk_userID = :userid")
    public List<OrderBean> findOrderByLandlordId(@Param("finshed") String status, @Param("userid") String userid);

}