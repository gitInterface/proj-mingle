package com.ispan.mingle.projmingle.repository;
    
    


import java.lang.Integer;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.domain.WorkPhotoBean;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 持久层
 *
 * @author makejava
 * @since 2024-01-30 15:13:16
 */
public interface WorkPhotoRepository extends JpaRepository<WorkPhotoBean, Integer> {

}

