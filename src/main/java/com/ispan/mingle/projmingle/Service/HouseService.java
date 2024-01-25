package com.ispan.mingle.projmingle.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.repository.HouseRepository;

@Service
@Transactional
public class HouseService {
	@Autowired
    private HouseRepository houseRepository;

    public List<HouseBean> findAllHouses() {
        return houseRepository.findAll();
    }

    public Optional<HouseBean> findById(Integer houseId) {
        return houseRepository.findById(houseId);
    }

    public HouseBean save(HouseBean house) {
        return houseRepository.save(house);
    }

    public void deleteById(Integer houseId) {
        houseRepository.deleteById(houseId);
    }
    
}