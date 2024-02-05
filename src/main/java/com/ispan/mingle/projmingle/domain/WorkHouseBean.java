package com.ispan.mingle.projmingle.domain;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Work_House")
@Component
public class WorkHouseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "fk_workID")
    private Integer workid;

    @Column(name = "fk_houseID")
    private Integer houseid;

    @Column(name = "isDeleted", columnDefinition = "bit")
    private boolean isDeleted;

}
