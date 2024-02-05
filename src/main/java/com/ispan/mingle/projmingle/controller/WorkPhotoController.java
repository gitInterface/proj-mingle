package com.ispan.mingle.projmingle.controller;
    
    



import java.lang.Integer;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.Service.WorkPhotoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


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

