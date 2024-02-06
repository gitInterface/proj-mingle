package com.ispan.mingle.projmingle.dto;


import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.util.DatetimeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor

public class LandlordOrderDTO {
    /*houseBean*/
    private String workName;
    private String houseName;
    private OrderBean order;
    public LandlordOrderDTO(String workName, String houseName, OrderBean order) {
        // 初始化DTO的屬性
        this.workName = workName;
        this.houseName = houseName;
        this.order = order;
        this.formatStartDate = DatetimeConverter.toString(order.getStartDate(), "yyyy-MM-dd HH:mm:ss");
        this.formatEndDate = DatetimeConverter.toString(order.getEndDate(), "yyyy-MM-dd HH:mm:ss");
        this.formatInvoiceDate = DatetimeConverter.toString(order.getInvoiceDate(), "yyyy-MM-dd HH:mm:ss");
    }
    private String formatEndDate;
    private String formatStartDate;
    private String formatInvoiceDate;


        /*orderBean*/
//    private Integer orderid;
//    private String userid;
//    private String status;
//    private String notes;
//    private String needs;
//    private Date startDate;
//    private Date endDate;
//    private boolean isCancelled;
//    private boolean isRefunded;
//    private boolean isUserAttend;
//    private String businessId;
//    private Date invoiceDate;
//    private String invoiceNumber;



}
