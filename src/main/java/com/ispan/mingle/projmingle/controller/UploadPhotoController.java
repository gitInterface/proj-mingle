package com.ispan.mingle.projmingle.controller;


import com.ispan.mingle.projmingle.config.UploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Controller
@CrossOrigin
public class UploadPhotoController {

    @Autowired
    private UploadConfig uploadConfig;

    @ResponseBody
    @PostMapping("/photoUploadControl")
    public String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
//      拿時間打亂檔名
        String newName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        System.out.println(newName);
//      抓properties的path
        String path = uploadConfig.getPath();
        File newPath = new File(path);
//      開資料夾
        if (!newPath.exists()) newPath.mkdir();
        try {
            File newFile = new File(newPath, newName);
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(path+"/"+newName);
        return path+newName;
    }
}
