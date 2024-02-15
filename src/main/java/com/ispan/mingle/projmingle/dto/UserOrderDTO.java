package com.ispan.mingle.projmingle.dto;

import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.util.DatetimeConverter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserOrderDTO {
    /* houseBean */
    private String workName;
    private String houseName;
    private OrderBean order;

    public UserOrderDTO(String workName, String houseName, OrderBean order) {
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

}
