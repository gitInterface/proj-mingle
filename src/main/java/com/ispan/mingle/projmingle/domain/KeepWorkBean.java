package com.ispan.mingle.projmingle.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "KeepWork")
@IdClass(KeepWorkId.class)
public class KeepWorkBean {

    @Id
    @ManyToOne
    @JoinColumn(name = "userID")
    private VolunteerBean volunteer;

    @Id
    @ManyToOne
    @JoinColumn(name = "workID")
    private WorkBean work;

}
