package com.ispan.mingle.projmingle.Service;


import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.repository.LandlordRepository;


@Service
@Transactional
public class LandlordService {

    @Resource
    private LandlordRepository landlordRepository;

    public LandlordBean getLandlordById(Integer id) {
        return landlordRepository.findById(id).orElse(null);
    }

    public Integer findByUserIDtoLordID(String id) { return landlordRepository.findByUserIDtoLordID(id); };

    public LandlordBean createOrUpdateLandlord(LandlordBean landlordBean) {
        LandlordBean existingLandlord = landlordRepository.findByUserid(landlordBean.getUserid());

        if (existingLandlord != null) {
            // 更新現有的LandlordBean
            existingLandlord.setCity(landlordBean.getCity());
            existingLandlord.setAddress(landlordBean.getAddress());
            existingLandlord.setFeature(landlordBean.getFeature());
            existingLandlord.setPet(landlordBean.getPet());
            // 設定其他欄位
            existingLandlord.setUpdatedAt(new Date());
            return landlordRepository.save(existingLandlord);
        } else {
            // 建立新的LandlordBean
            return landlordRepository.save(landlordBean);
        }
    }

}