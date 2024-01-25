package com.ispan.mingle.projmingle.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.ispan.mingle.projmingle.domain.CityBean;
import com.ispan.mingle.projmingle.repository.CityRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;


    public List<CityBean> getAllCities() {
        return cityRepository.findAll();
    }
}
