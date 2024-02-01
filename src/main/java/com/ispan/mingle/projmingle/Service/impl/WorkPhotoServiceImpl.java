package com.ispan.mingle.projmingle.Service.impl;
    
    


import java.lang.Integer;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.Service.WorkPhotoService;
import com.ispan.mingle.projmingle.repository.WorkPhotoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * 业务层
 *
 * @author makejava
 * @since 2024-01-30 15:13:16
 */
@Service
public class WorkPhotoServiceImpl extends WorkPhotoService {

    public WorkPhotoServiceImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }
}

