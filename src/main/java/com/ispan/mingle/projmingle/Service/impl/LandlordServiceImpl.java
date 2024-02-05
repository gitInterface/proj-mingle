package com.ispan.mingle.projmingle.Service.impl;
    
    


import java.lang.Integer;
import com.ispan.mingle.projmingle.domain.LandlordBean;
import com.ispan.mingle.projmingle.service.LandlordService;
import com.ispan.mingle.projmingle.repository.LandlordRepository;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * 业务层
 *
 * @author makejava
 * @since 2024-01-30 10:44:24
 */
@Service
public class LandlordServiceImpl implements LandlordService {

	@Resource
    private LandlordRepository landlordRepository;

    @Override
    public Integer findByUserIDtoLordID(String id) { return landlordRepository.findByUserIDtoLordID(id); };

}

