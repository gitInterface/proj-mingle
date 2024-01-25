package com.ispan.mingle.projmingle.domain;


import org.springframework.stereotype.Component;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Worktype")
@Component
public class WorktypeBean {
    

    /**  打工類型 */
    @Id
    @Column(name = "worktypeID",
    columnDefinition = "nvarchar(10)")
    private String worktypeID;

    // @OneToMany(mappedBy = "workType",cascade = CascadeType.ALL)
    // private Set<WorkBean> allworks;


}
