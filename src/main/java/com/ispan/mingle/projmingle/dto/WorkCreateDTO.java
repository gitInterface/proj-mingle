package com.ispan.mingle.projmingle.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class WorkCreateDTO {

    private Integer workID;
    private Integer fkLandlordID;
    private String worktype;
    private String workName;
    private String status;
    private String notes;
    private String city;
    private String address;
    private Date startDate;
    private Date endDate;
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
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDeleted;
    private Integer views;
    private Integer attendance;
    private Integer minPeriod;
    private String sessionToken;

    // Constructors, getters, and setters

}

