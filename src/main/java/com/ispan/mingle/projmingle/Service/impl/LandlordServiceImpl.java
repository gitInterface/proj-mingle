package com.ispan.mingle.projmingle.Service.impl;
    
    


import java.lang.Integer;

import com.ispan.mingle.projmingle.Service.LandlordService;
import com.ispan.mingle.projmingle.repository.LandlordRepository;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * 业务层
 *
 * @author makejava
 * @since 2024-01-30 10:44:24
 */
@Service
public class LandlordServiceImpl extends LandlordService {

	@Resource
    private LandlordRepository landlordRepository;

    @Override
    public Integer findByUserIDtoLordID(String id) { return landlordRepository.findByUserIDtoLordID(id); };

}

