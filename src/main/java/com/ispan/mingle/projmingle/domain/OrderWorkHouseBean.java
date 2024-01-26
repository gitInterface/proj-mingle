package com.ispan.mingle.projmingle.domain;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Order_Work_House")
@Component
@IdClass(OrderWorkHousePk.class)
public class OrderWorkHouseBean {

    @Id
    @Column(name = "fk_orderID")
    private Integer orderid;
    
    
    @Id
    @Column(name = "fk_ID")
    private Integer workhouseid;

 
}
