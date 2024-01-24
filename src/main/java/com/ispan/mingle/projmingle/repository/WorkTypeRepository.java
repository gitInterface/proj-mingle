package com.ispan.mingle.projmingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ispan.mingle.projmingle.domain.WorkTypeBean;

public interface WorkTypeRepository extends JpaRepository<WorkTypeBean, String> {
}
