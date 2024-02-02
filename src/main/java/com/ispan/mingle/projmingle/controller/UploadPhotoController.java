package com.ispan.mingle.projmingle.controller;


import com.ispan.mingle.projmingle.config.UploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/upload")
public class UploadPhotoController {

    @Autowired
    private UploadConfig uploadConfig;

    @ResponseBody
    @PostMapping("/photoUploadControl")
    public String upload(MultipartFile file ,@RequestHeader("sessionToken") String session) {
//        System.out.println(session);

        String fileName = file.getOriginalFilename();
//      拿時間打亂檔名
        String newName = fileName + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        System.out.println(newName);
//      抓properties的path
        String path = uploadConfig.getPath() + session ;
        File newPath = new File(path);
//      開資料夾
        if (!newPath.exists()) newPath.mkdirs();
        try {
            File newFile = new File(newPath, newName);
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(path+"/"+newName);
        return path+"/"+newName;
    }
    @ResponseBody
    @PostMapping("/photoDeleteController")
    public void delete(@RequestBody String url,@RequestHeader("sessionToken") String session) {
//        System.out.println("delete:" + url);
        try{
            File f = new File(url);
            f.delete();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    //頁面初始
    @ResponseBody
    @PostMapping("/photoDeleteOnLoadController")
    public void deleteOnLoad(@RequestHeader("sessionToken") String session) {
        try{
            File f = new File(uploadConfig.getPath() + session);
            if (f.exists()) {
                FileSystemUtils.deleteRecursively(f);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
