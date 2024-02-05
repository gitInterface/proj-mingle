package com.ispan.mingle.projmingle.Service;

import com.ispan.mingle.projmingle.config.UploadConfig;
import com.ispan.mingle.projmingle.controller.UploadPhotoController;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.domain.WorkPhotoBean;
import com.ispan.mingle.projmingle.dto.WorkPhotoDTO;
import com.ispan.mingle.projmingle.repository.WorkPhotoRepository;
import com.ispan.mingle.projmingle.util.DatetimeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import com.ispan.mingle.projmingle.domain.CityBean;
import com.ispan.mingle.projmingle.repository.CityRepository;

@Service
public class WorkPhotoService {

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private WorkPhotoRepository workPhotoRepository;
    @Autowired
    private UploadPhotoController photoController;
    @Autowired
    private ModelMapper modelMapper;

    public WorkPhotoService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public void getPhoto(String session,Integer workid) {
        String folderPath = uploadConfig.getPath() + session;
        File folder = new File(folderPath);
        Date date = DatetimeConverter.getCurrentDate();

        if (folder.isDirectory()) {
            File[] photoFiles = folder.listFiles((dir, name) ->
                    name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".webp"));

            if (photoFiles != null) {
                for (File photoFile : photoFiles) {
                    try {
                        // 拿到檔案大小
                        Integer fileSize = Math.toIntExact(Files.size(photoFile.toPath()));

                        // 拿到副檔名
                        String fileExtension = getFileExtension(photoFile.getName());

                        // 轉成byte
                        byte[] fileBytes = Files.readAllBytes(photoFile.toPath());

                        //格式化輸出
                        System.out.println("------------------------------");
                        System.out.println("File: " + photoFile.getName());
                        System.out.println("Size: " + fileSize + " bytes");
                        System.out.println("Content-Type: " + fileExtension);
                        System.out.println("Byte array length: " + fileBytes.length);
                        System.out.println("------------------------------");
                        WorkPhotoDTO dto = new WorkPhotoDTO();
                        dto.setPhotoSize(fileSize);
                        dto.setCreatedAt(date);
                        dto.setUpdatedAt(date);
                        dto.setContentType(fileExtension);
                        dto.setPhoto(fileBytes);
                        dto.setWorkid(workid);
                        dto.setIsDeleted(false);
                        WorkPhotoBean workPhoto = modelMapper.map(dto, WorkPhotoBean.class);
                        workPhotoRepository.save(workPhoto);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        photoController.deleteOnLoad(session);

    }
    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}
