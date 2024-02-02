package com.ispan.mingle.projmingle.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.ispan.mingle.projmingle.config.UploadConfig;
import com.ispan.mingle.projmingle.controller.UploadPhotoController;
import com.ispan.mingle.projmingle.domain.WorkPhotoBean;
import com.ispan.mingle.projmingle.dto.WorkPhotoDTO;
import com.ispan.mingle.projmingle.util.DatetimeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ispan.mingle.projmingle.domain.HousePhotoBean;
import com.ispan.mingle.projmingle.repository.HousePhotoRepository;
import org.springframework.stereotype.Service;

@Service
public class HousePhotoService {
    @Autowired
    private HousePhotoRepository housePhotoRepository;
    @Autowired
    private UploadConfig uploadConfig;
    @Autowired
    private UploadPhotoController photoController;
    @Autowired
    private ModelMapper modelMapper;

    // Create
    public HousePhotoBean saveHousePhoto(HousePhotoBean housePhoto) {
        return housePhotoRepository.save(housePhoto);
    }

    // Read
    public Optional<HousePhotoBean> getHousePhotoById(Integer photoId) {
        return housePhotoRepository.findById(photoId);
    }

    public List<HousePhotoBean> getAllHousePhotos() {
        return housePhotoRepository.findAll();
    }

    // Update
    public HousePhotoBean updateHousePhoto(HousePhotoBean housePhoto) {
        return housePhotoRepository.save(housePhoto);
    }

    // Update by photoid
    public HousePhotoBean updateHousePhotoById(Integer photoId, HousePhotoBean updatedHousePhoto) {
        Optional<HousePhotoBean> existingHousePhotoOptional = housePhotoRepository.findById(photoId);

        if (existingHousePhotoOptional.isPresent()) {
            HousePhotoBean existingHousePhoto = existingHousePhotoOptional.get();
            // Update the properties you want to allow modification
            existingHousePhoto.setPhoto(updatedHousePhoto.getPhoto());
            existingHousePhoto.setPhotoSize(updatedHousePhoto.getPhotoSize());
            existingHousePhoto.setContentType(updatedHousePhoto.getContentType());
            existingHousePhoto.setUpdatedAt(updatedHousePhoto.getUpdatedAt());

            // Save the updated entity
            return housePhotoRepository.save(existingHousePhoto);
        } else {
            throw new NoSuchElementException("Photo with ID " + photoId + " not found");
        }
    }

    // Delete
    public void deleteHousePhoto(Integer photoId) {
        housePhotoRepository.deleteById(photoId);
    }

    public void getPhoto(String session,Integer houseId) {
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
                        HousePhotoBean bean = new HousePhotoBean();
                        bean.setPhotoSize(fileSize);
                        bean.setCreatedAt(date);
                        bean.setUpdatedAt(date);
                        bean.setContentType(fileExtension);
                        bean.setPhoto(fileBytes);
                        bean.setHouseid(houseId);
                        bean.setIsDeleted('0');
                        housePhotoRepository.save(bean);

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