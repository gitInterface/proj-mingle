package com.ispan.mingle.projmingle.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class WorkModifySubmitWorkDTO {
    private Integer workid;
    private String worktype;
    private String name;
    private String notes;
    private String city;
    private String address;
    private Date startDate;
    private Date endDate;
    private Integer minPeriod;
    private Integer maxAttendance;
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
    private Date updatedAt;
    private Boolean isOnShelf;
}
