package com.ispan.mingle.projmingle.service;
    
    


import java.lang.Integer;

import com.ispan.mingle.projmingle.domain.LandlordBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


/**
 * 业务层
 *
 * @author makejava
 * @since 2024-01-30 10:44:24
 */
public interface LandlordService {

    Integer findByUserIDtoLordID(String id);
}

