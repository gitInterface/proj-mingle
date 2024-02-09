package com.ispan.mingle.projmingle.controller;

import com.ispan.mingle.projmingle.Service.LandlordService;
import com.ispan.mingle.projmingle.domain.LandlordBean;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 控制层
 *
 * @author makejava
 * @since 2024-01-30 10:44:24
 */
@RestController
@RequestMapping("/landlord")
@AllArgsConstructor
@CrossOrigin
public class LandlordController {

	@Autowired
	private LandlordService landlordService;

	@GetMapping("/userIDtoLordID/{id}")
	public Integer getLordID(@PathVariable String id) {
		return landlordService.findByUserIDtoLordID(id);
	}

	@PostMapping("/createOrUpdateLandlord")
	public LandlordBean createOrUpdateLandlord(@RequestBody LandlordBean landlordBean) {
		return landlordService.createOrUpdateLandlord(landlordBean);
	}
}
