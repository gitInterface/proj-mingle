package com.ispan.mingle.projmingle.dto;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.domain.WorkBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class WorkModifyDTO {
    private Integer workid;
    private Integer landlordid;
    private String worktype;
    private String name;
    private String status;
    private String notes;
    private String city;
    private String address;
    private Date startDate;
    private Date endDate;
    private Integer minPeriod;
    private Integer maxAttendance;
    private Integer attendance;
    private String description;
    private String workTime;
    private String vacation;
    private String ageRestriction;
    private String genderRestriction;
    private String educationRestriction;
    private String experienceRestriction;
    private String languageRestriction;
    private String licenseRestriction;
    private String benefits;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDeleted;
    private boolean isOnShelf;

    // 工作照片(沒被刪除的)
    private List<String> photosBase64;

    // 上面的照片id(前端回傳用，不想傳base64回來)
    private List<Integer> photosID;

}