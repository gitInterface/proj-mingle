package com.ispan.mingle.projmingle.domain;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "Work")
@Component
public class WorkBean {

    /** 打工ID */
    @Id
    @Column(name = "workID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workid;

    /** 房東ID */
    @Column(name = "fk_landlordID", columnDefinition = "int")
    private Integer landlordid;

    /** 工作類型 */
    @Column(name = "fk_workType", columnDefinition = "nvarchar(10)")
    private String worktype;

    /** 工作名稱 */
    @Column(name = "workName", columnDefinition = "nvarchar(30)")
    private String name;

    /** 工作項目狀態 */
    @Column(name = "status", columnDefinition = "varchar(20)")
    private String status;

    /** 備註 */
    @Column(name = "notes", columnDefinition = "nvarchar(100)")
    private String notes;

    /** 城市 */
    @Column(name = "fk_city", columnDefinition = "nvarchar(3)")
    private String city;

    /** 工作地址 */
    @Column(name = "address", columnDefinition = "nvarchar(200)")
    private String address;

    /** 開始預定工作日期 */
    @Column(name = "startDate", columnDefinition = "datetime")
    private Date startDate;

    /** 結束預定工作日期 */
    @Column(name = "endDate", columnDefinition = "datetime")
    private Date endDate;

    /** 最小報名天數 */
    @Column(name = "minPeriod", columnDefinition = "int")
    private Integer minPeriod;

    /** 最高招募人數 */
    @Column(name = "maxAttendance", columnDefinition = "int")
    private Integer maxAttendance;

    /** 已報名人數 */
    @Column(name = "attendance", columnDefinition = "int")
    private Integer attendance;

    /** 打工項目描述 */
    @Column(name = "description", columnDefinition = "nvarchar(500)")
    private String description;

    /** 工作時段 */
    @Column(name = "workTime", columnDefinition = "nvarchar(100)")
    private String workTime;

    /** 休假制度 */
    @Column(name = "vacation", columnDefinition = "nvarchar(100)")
    private String vacation;

    /** 年齡限制 */
    @Column(name = "ageRestriction", columnDefinition = "nvarchar(100)")
    private String ageRestriction;

    /** 性別限制 */
    @Column(name = "genderRestriction", columnDefinition = "nvarchar(20)")
    private String genderRestriction;

    /** 學歷要求 */
    @Column(name = "educationRestriction", columnDefinition = "nvarchar(100)")
    private String educationRestriction;

    /** 工作經歷要求 */
    @Column(name = "experienceRestriction", columnDefinition = "nvarchar(100)")
    private String experienceRestriction;

    /** 語言要求 */
    @Column(name = "languageRestriction", columnDefinition = "nvarchar(100)")
    private String languageRestriction;

    /** 駕照要求 */
    @Column(name = "licenseRestriction", columnDefinition = "nvarchar(100)")
    private String licenseRestriction;

    /** 打工福利 */
    @Column(name = "benefits", columnDefinition = "nvarchar(100)")
    private String benefits;

    /** 建立時間 */
    @Column(name = "createdAt", columnDefinition = "datetime")
    private Date createdAt;

    /** 更新時間 */
    @Column(name = "updatedAt", columnDefinition = "datetime")
    private Date updatedAt;

    /** 是否刪除 */
    @Column(name = "isDeleted", columnDefinition = "bit")
    private Boolean isDeleted;

    /** 瀏覽量 */
    @Column(name = "views", columnDefinition = "int")
    private Integer views;

    @Column(name = "isOnShelf", columnDefinition = "boolean")
    private boolean isOnShelf;

    @Transient
    private List<String> photosBase64;

    @Transient
    private boolean Kept;

    

    // 關連到 WorkPhotoBean (一對多)
    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "workBean")
    private List<WorkPhotoBean> workPhotoBeans;

    // 關連到 CityBean (多對一)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_city", referencedColumnName = "city", insertable = false, updatable = false)
    private CityBean cityBean;

    // 獲取所有未被刪除的 WorkPhotoBeans
    public List<WorkPhotoBean> getUndeletedWorkPhotoBeans() {
        return workPhotoBeans.stream()
                .filter(photo -> !photo.getIsDeleted())
                .collect(Collectors.toList());
    }

}
