package com.ispan.mingle.projmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.mingle.projmingle.domain.HousePhotoBean;

public interface HousePhotoRepository  extends JpaRepository<HousePhotoBean, Integer> {

    @Query("SELECT h FROM HousePhotoBean h WHERE h.photoid = :photoid")
    List<HousePhotoBean> findAllById(Integer photoid);
    
} 
