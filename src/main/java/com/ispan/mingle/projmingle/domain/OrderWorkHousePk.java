package com.ispan.mingle.projmingle.domain;

import java.io.Serializable;

import lombok.Data;


@Data
public class OrderWorkHousePk implements Serializable {
    
    private Integer orderid;

    private Integer workhouseid;

}
