package com.ispan.mingle.projmingle.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.repository.VolunteerDetailRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VolunteerDetailService {

    @Autowired
    private VolunteerDetailRepository volunteerDetailRepository;

    public VolunteerDetailBean findById(String id) {
        return volunteerDetailRepository.findById(id).orElse(null);
    }

    public VolunteerDetailBean update(VolunteerDetailBean volunteerDetailBean) {
        VolunteerDetailBean newBean = volunteerDetailRepository.save(volunteerDetailBean);
        return newBean;
    }
}