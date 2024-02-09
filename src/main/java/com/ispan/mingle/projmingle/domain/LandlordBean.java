package com.ispan.mingle.projmingle.domain;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Landlord")
public class LandlordBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "landlordID")
    private Integer landlordid;

    @Column(name = "fk_userID", columnDefinition = "varchar", length = 100)
    private String userid;

    @Column(name = "fk_city", columnDefinition = "nvarchar", length = 3)
    private String city;

    @Column(name = "address", columnDefinition = "nvarchar", length = 50)
    private String address;

    @Column(name = "feature", columnDefinition = "nvarchar", length = 150)
    private String feature;

    @Column(name = "pet", columnDefinition = "nvarchar", length = 20)
    private String pet;

    @Column(name = "isPerfectLord", columnDefinition = "bit")
    private Boolean isPerfectLord;

    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "createdAt", columnDefinition = "datetime")
    private Date createdAt;

    @Column(name = "updatedAt", columnDefinition = "datetime")
    private Date updatedAt;

}
