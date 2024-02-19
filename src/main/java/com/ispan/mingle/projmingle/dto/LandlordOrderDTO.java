package com.ispan.mingle.projmingle.dto;

import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.util.DatetimeConverter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LandlordOrderDTO {
    /* houseBean */
    private String userName;
    private String workName;
    private String houseName;
    private OrderBean order;

    private String formatEndDate;
    private String formatStartDate;
    private String formatCreateDate;

    public LandlordOrderDTO(String userName, String workName, String houseName, OrderBean order) {
        // 初始化DTO的屬性
        this.userName = userName;
        this.workName = workName;
        this.houseName = houseName;
        this.order = order;
        this.formatStartDate = DatetimeConverter.toString(order.getStartDate(), "yyyy-MM-dd HH:mm:ss");
        this.formatEndDate = DatetimeConverter.toString(order.getEndDate(), "yyyy-MM-dd HH:mm:ss");
        this.formatCreateDate = DatetimeConverter.toString(order.getCreatedAt(), "yyyy-MM-dd HH:mm:ss");
    }

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
