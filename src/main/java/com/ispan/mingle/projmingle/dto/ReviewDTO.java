package com.ispan.mingle.projmingle.dto;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;


@Data
@Component
public class ReviewDTO {

    
    /**評論的幫助者id */
    private  String  userid;

    /**評論的幫助者姓名 */
    private String username;

    /**評論的幫助者大頭照 */
    private String image;
    
    /**評論的幫助者大頭照類型 */
    private String photoType;


    /**評論的幫助者國籍 */
    private String country;

    /**評論的幫助者訂單的工作id */
    private Integer workid;

    /**評論的幫助者訂單的工作類型 */
    private String worktype;

    /**評論的幫助者訂單的工作名稱 */
    private String workName;

    /**評論的幫助者訂單的工作地點 */
    private String city;

    /**評論的幫助者訂單的工作天數 */
    private Long days;

    /** 評論的幫助者開始工作日期 */ 
    private String startDate;

    /** 評論的幫助者結束工作日期 */
    private String endDate;

    /** 評論的幫助者訂單的房屋 */
    private List<Integer> houseid;

    /** 評論的幫助者訂單的房屋類型 */
    private List<String> houseType;

    /** 評論的幫助者訂單的房屋名稱 */
    private List<String> houseName;

    /** 評論的幫助者訂單編號 */
    private Integer orderid;

    /** 評論的幫助者訂單的報名人數 */
    private Integer numbers;











}
