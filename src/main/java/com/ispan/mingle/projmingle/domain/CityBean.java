package com.ispan.mingle.projmingle.domain;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="City")
@Component
public class CityBean {
    @Id
    @Column(name = "city",
    columnDefinition = "nvarchar(3)")
    private String city;

    @Column(name = "area",
    columnDefinition = "nvarchar(4)")
    private String area;

}
