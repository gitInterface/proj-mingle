package com.ispan.mingle.projmingle.Service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.repository.HouseRepository;

@Service
@Transactional
public class HouseService {
    @Autowired
	private HouseRepository houseRepository = null;

    public List<HouseBean> find(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return houseRepository.find(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public HouseBean findById(Integer id) {
		if(id!=null) {
			Optional<HouseBean> optional = houseRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}
    
	public long count(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return houseRepository.count(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}