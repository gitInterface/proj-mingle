package com.ispan.mingle.projmingle.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ispan.mingle.projmingle.domain.HousePhotoBean;
import com.ispan.mingle.projmingle.repository.HousePhotoRepository;

public class HousePhotoService {
    @Autowired
    private HousePhotoRepository housePhotoRepository;

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

}