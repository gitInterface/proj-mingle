package com.ispan.mingle.projmingle.controller;
    
    



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.WorkPhotoService;


/**
 * 控制层
 *
 * @author makejava
 * @since 2024-01-30 15:13:16
 */
@RestController
@RequestMapping("/workPhoto")
@CrossOrigin
public class WorkPhotoController {

 	@Autowired
	private WorkPhotoService workPhotoService;

//	 @PostMapping("/savePhoto")
//	 public void savePhoto(@RequestBody String session) {
//		 workPhotoService.getPhoto(session);
//	 }


}

