package com.ispan.mingle.projmingle.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.repository.WorkRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class WorkService {
    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private GoogleMapsGeocodingService geocodingService;

    public List<WorkBean> getAllWorkBeans() {
        return workRepository.findAll();
    }

    public List<String> getFormattedAddresses() {
        List<WorkBean> workBeans = getAllWorkBeans();
        return geocodingService.getFormattedAddresses(workBeans);
    }

    
}
