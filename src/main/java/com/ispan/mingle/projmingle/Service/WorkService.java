package com.ispan.mingle.projmingle.Service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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


    public List<WorkBean> getHotWorks() {
        Sort sort = Sort.by(Sort.Direction.DESC, "views");
        return workRepository.findAll(sort);
    }
    
    public List<WorkBean> getLatestWorks() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return workRepository.findAll(sort);
    }

    public List<WorkBean> getOldestWorks() {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        return workRepository.findAll(sort);
    }
    
    public List<WorkBean> getDeadlineWorks() {
        Sort sort = Sort.by(Sort.Direction.ASC, "startDate");
        return workRepository.findAll(sort);
    }

    public List<WorkBean> getWorksByRemainingSpotsAsc() {
        List<WorkBean> works = workRepository.findAll();
        works.sort(Comparator.comparingInt((WorkBean w) -> w.getMaxAttendance() - w.getAttendance()));
        return works;
    }
    
    public List<WorkBean> getWorksByRemainingSpotsDesc() {
        List<WorkBean> works = workRepository.findAll();
        works.sort(Comparator.comparingInt((WorkBean w) -> w.getMaxAttendance() - w.getAttendance()).reversed());
        return works;
    }

    public List<String> getFormattedAddresses() {
        List<WorkBean> workBeans = workRepository.findAll();
        return geocodingService.getFormattedAddresses(workBeans);
    }

    
}
