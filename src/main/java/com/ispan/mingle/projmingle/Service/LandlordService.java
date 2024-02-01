package com.ispan.mingle.projmingle.Service;
    
    


import java.lang.Integer;

import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.repository.LandlordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class LandlordService {

    @Resource
    private LandlordRepository landlordRepository;

    public Integer findByUserIDtoLordID(String id) { return landlordRepository.findByUserIDtoLordID(id); };

}