package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.domain.WorkBean;

public interface OrderRepository extends JpaRepository<OrderBean, Integer> {

    @Query("SELECT vd FROM OrderBean o " +
            "JOIN VolunteerBean v ON o.userid = v.userid " +
            "JOIN VolunteerDetailBean vd ON o.userid = vd.userid " +
            "WHERE o.orderid = :orderid")
    public VolunteerDetailBean findVolunteerDetailByOrderId(@Param("orderid") Integer orderid);


    @Query("SELECT w FROM OrderBean o " +
            "JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
            "JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
            "JOIN WorkBean w ON wh.workid = w.workid " +
            "WHERE o.orderid = :orderid")
    public WorkBean findWorkBeanByOrderId(@Param("orderid") Integer orderid);


    @Query("SELECT h FROM OrderBean o " +
            "JOIN OrderWorkHouseBean owh ON o.orderid = owh.orderid " +
            "JOIN WorkHouseBean wh ON owh.workhouseid = wh.id " +
            "JOIN HouseBean h ON wh.houseid = h.houseid " +
            "WHERE o.orderid = :orderid")
    public List<HouseBean> findHouseBeanByOrderId(@Param("orderid") Integer orderid);

}
