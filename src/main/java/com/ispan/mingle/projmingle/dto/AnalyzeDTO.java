package com.ispan.mingle.projmingle.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.ReviewBean;
import com.ispan.mingle.projmingle.domain.WorkBean;

import lombok.Data;

@Data
@Component
public class AnalyzeDTO {
    
    /**房東id */
    private Integer landlordid;

    /**房東開設工作 */
    private List<WorkBean> work;

    /**房東擁有的訂單*/
    private List<OrderBean> order;

    /**房東擁有的評價 */
    private List<ReviewBean> review;

    
    /** 房東擁有的訂單數 */
    private Integer totalorder;

        
    /** 房東擁有的訂單總報名人數 */
    private Integer ordernumbers;

    /** 透過房東id查詢所有工作瀏覽量總和 */
    private Integer totalview;

    /** 房東所有訂單平均評價分數 */
    private Double avgstar;

    /** 房東所有工作總評價數 */
    private Integer totalreview;



    
}
