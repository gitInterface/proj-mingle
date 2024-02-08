package com.ispan.mingle.projmingle.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ispan.mingle.projmingle.domain.CityBean;
import com.ispan.mingle.projmingle.domain.WorkPhotoBean;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class WorkModifyListDTO {
    private List<String> photosBase64;
    private Integer workid;
    private Integer landlordid;
    private String worktype;
    private String name;
    private String city;
    private Date startDate;
    private Date endDate;
    private Date updatedAt;
    private Integer attendance;
    private Integer maxAttendance;
    private String status;
    private boolean isOnShelf;
    private boolean Kept;
    private String workTime;
    private String vacation;
    private String benefits;
    private Integer minPeriod;
    private String ageRestriction;
    private String genderRestriction;
    private String languageRestriction;
    private Integer views;
    private String educationRestriction;
    private String experienceRestriction;
    private String licenseRestriction;
    private String notes;
    private String address;
    private Date createdAt;
    private String description;
    private Boolean isDeleted;
}
