package com.ispan.mingle.projmingle.repository;

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

}