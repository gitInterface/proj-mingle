package com.ispan.mingle.projmingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.mingle.projmingle.domain.OrderWorkHouseBean;
import com.ispan.mingle.projmingle.domain.OrderWorkHousePk;

public interface OrderWorkHouseRepository  extends JpaRepository<OrderWorkHouseBean, OrderWorkHousePk> {

    
}
