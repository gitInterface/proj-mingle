package com.ispan.mingle.projmingle.domain;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Report")
@Component
public class ReportBean {

    /** 檢舉ID */
    @Id
    @Column(name = "reportID", 
    columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportID;

    /** 工作ID */
    @Column(name = "fk_workID",
    columnDefinition = "int")
    private Integer workID;

    /** 違規類型 */
    @Column(name = "type",
    columnDefinition = "nvarchar(50)")
    private String type;

    /** 詳細原因 */
    @Column(name = "reason",
    columnDefinition = "nvarchar(500)")
    private String reason;

    /** 檢舉狀態 */
    @Column(name = "status",
    columnDefinition = "int")
    private Integer status;

    /** 創建時間 */
    @Column(name = "createdAt",
    columnDefinition = "datetime")
    private Date createdAt;

    /** 更新時間 */
    @Column(name = "updatedAt",
    columnDefinition = "datetime")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
