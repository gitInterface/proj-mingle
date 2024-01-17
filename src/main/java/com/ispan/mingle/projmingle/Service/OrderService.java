package com.ispan.mingle.projmingle.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.repository.VolunteerDetailRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private VolunteerDetailRepository volunteerDetailRepository;

    public VolunteerDetailBean selectVolunteerDetail (String id) {
       VolunteerDetailBean  detail = volunteerDetailRepository.findById(id).orElse(null);
        return detail;
    }


}
