package com.ispan.mingle.projmingle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.mingle.projmingle.domain.HousePhotoBean;

public interface HousePhotoRepository extends JpaRepository<HousePhotoBean, Integer> {
    // houseid找出所有「沒被刪除」的照片
    @Query("SELECT h FROM HousePhotoBean h WHERE h.houseid = :houseid AND h.isDeleted !='1' ")
    List<HousePhotoBean> findAllByHouseId(Integer houseid);

    Optional<HousePhotoBean> findByPhoto(byte[] photo);

    void deleteByHouseid(Integer houseid);
}
