package com.ispan.mingle.projmingle.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.repository.VolunteerDetailRepository;
import com.ispan.mingle.projmingle.repository.Work.WorkRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private VolunteerDetailRepository volunteerDetailRepository;

    @Autowired
    private WorkRepository workRepository;


    /** 透過會員id查詢會員資料 */
    public VolunteerDetailBean selectVolunteerDetail (String id) {
       VolunteerDetailBean  detail = volunteerDetailRepository.findById(id).orElse(null);
        return detail;
    }

    /** 透過工作id查詢工作資料 */
    public WorkBean selectWorkDetail (Integer id) {
        WorkBean  detail = workRepository.findById(id).orElse(null);
        return detail;
    }




}
