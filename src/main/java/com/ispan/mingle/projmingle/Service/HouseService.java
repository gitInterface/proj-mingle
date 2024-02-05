package com.ispan.mingle.projmingle.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.domain.HousePhotoBean;
import com.ispan.mingle.projmingle.repository.HousePhotoRepository;
import com.ispan.mingle.projmingle.repository.HouseRepository;
import com.ispan.mingle.projmingle.util.DatetimeConverter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class HouseService {
    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HousePhotoRepository housePhotoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HousePhotoService housePhotoService;

    public List<HouseBean> findAllHouses() {
        return houseRepository.findAll();
    }

    public List<HouseBean> findAllHousesWithPhotos() {
        String jpql = "SELECT DISTINCT h FROM HouseBean h JOIN FETCH h.housePhotos";
        List<HouseBean> houses = entityManager.createQuery(jpql, HouseBean.class).getResultList();
        return houses;
    }

    public Optional<HouseBean> findById(Integer houseId) {
        return houseRepository.findById(houseId);
    }

    public HouseBean modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer houseid = obj.isNull("houseid") ? null : obj.getInt("houseid");
            Integer lordid = obj.isNull("lordid") ? null : obj.getInt("lordid");
            String houseType = obj.isNull("houseType") ? null : obj.getString("houseType");
            String city = obj.isNull("city") ? null : obj.getString("city");
            String name = obj.isNull("name") ? null : obj.getString("name");
            String description = obj.isNull("description") ? null : obj.getString("description");
            String address = obj.isNull("address") ? null : obj.getString("address");
            String postCode = obj.isNull("postCode") ? null : obj.getString("postCode");
            Integer beds = obj.isNull("beds") ? null : obj.getInt("beds");
            String status = obj.isNull("status") ? null : obj.getString("status");
            String notes = obj.isNull("notes") ? null : obj.getString("notes");
            Character hasWifi = obj.isNull("hasWifi") ? null : obj.getString("hasWifi").charAt(0);
            Character hasTV = obj.isNull("hasTV") ? null : obj.getString("hasTV").charAt(0);
            Character hasKitchen = obj.isNull("hasKitchen") ? null : obj.getString("hasKitchen").charAt(0);
            Character hasLaundry = obj.isNull("hasLaundry") ? null : obj.getString("hasLaundry").charAt(0);
            Character hasParkingLot = obj.isNull("hasParkingLot") ? null : obj.getString("hasParkingLot").charAt(0);
            Character hasAirconditioner = obj.isNull("hasAirconditioner") ? null
                    : obj.getString("hasAirconditioner").charAt(0);
            Character hasPersonalSpace = obj.isNull("hasPersonalSpace") ? null
                    : obj.getString("hasPersonalSpace").charAt(0);
            Character hasPool = obj.isNull("hasPool") ? null : obj.getString("hasPool").charAt(0);
            Character hasGym = obj.isNull("hasGym") ? null : obj.getString("hasGym").charAt(0);
            String tempcreatedAt = obj.isNull("tempcreatedAt") ? null : obj.getString("tempcreatedAt");
            String tempupdatedAt = obj.isNull("tempupdatedAt") ? null : obj.getString("tempupdatedAt");
            Character isDeleted = obj.isNull("isDeleted") ? null : obj.getString("isDeleted").charAt(0);

            if (houseid != null && houseRepository.existsById(houseid)) {
                Optional<HouseBean> optional = houseRepository.findById(houseid);
                if (optional.isPresent()) {
                    HouseBean update = optional.get();
                    update.setHouseid(houseid);
                    update.setLordid(lordid);
                    update.setHouseType(houseType);
                    update.setCity(city);
                    update.setName(name);
                    update.setDescription(description);
                    update.setAddress(address);
                    update.setPostCode(postCode);
                    update.setBeds(beds);
                    update.setStatus(status);
                    update.setNotes(notes);
                    update.setHasWifi(hasWifi);
                    update.setHasTV(hasTV);
                    update.setHasKitchen(hasKitchen);
                    update.setHasLaundry(hasLaundry);
                    update.setHasParkingLot(hasParkingLot);
                    update.setHasAirconditioner(hasAirconditioner);
                    update.setHasPersonalSpace(hasPersonalSpace);
                    update.setHasPool(hasPool);
                    update.setHasGym(hasGym);
                    update.setCreatedAt(DatetimeConverter.parse(tempcreatedAt, "yyyy-MM-dd"));
                    update.setUpdatedAt(DatetimeConverter.parse(tempupdatedAt, "yyyy-MM-dd"));
                    update.setIsDeleted(isDeleted);

                    // Detach the HouseBean from the EntityManager
                    entityManager.detach(update);

                    // Update housePhotos
                    // Check if "housePhotos" array exists and is not empty in the JSON
                    if (obj.has("housePhotos") && !obj.getJSONArray("housePhotos").isEmpty()) {
                        // Create a new list to store the update photos
                        List<HousePhotoBean> updatedPhotos = new ArrayList<>();
                        JSONArray photosArray = obj.getJSONArray("housePhotos");

                        // Remove existing HousePhotoBean entries associated with the selected houseid
                        housePhotoRepository.deleteByHouseid(update.getHouseid());

                        for (int i = 0; i < photosArray.length(); i++) {
                            JSONObject photoObject = photosArray.getJSONObject(i);
                            HousePhotoBean updatedPhoto = new HousePhotoBean();

                            // Set photo properties from the photoObject
                            String photoData = photoObject.getString("photo");
                            // Convert Base64-encoded string to byte array
                            byte[] decodedPhotoData = Base64.getDecoder().decode(photoData);
                            updatedPhoto.setPhoto(decodedPhotoData);
                            updatedPhoto.setContentType(photoObject.getString("contentType"));
                            updatedPhoto.setPhotoSize(photoObject.getInt("photoSize"));
                            updatedPhoto.setCreatedAt(
                                    DatetimeConverter.parse(photoObject.getString("createdAt"), "yyyy-MM-dd"));
                            updatedPhoto.setUpdatedAt(
                                    DatetimeConverter.parse(photoObject.getString("updatedAt"), "yyyy-MM-dd"));
                            updatedPhoto.setIsDeleted(photoObject.getString("isDeleted").charAt(0));

                            // Set the house reference for the new photo
                            updatedPhoto.setHouseid(update.getHouseid());

                            updatedPhotos.add(updatedPhoto);
                        }

                        // Set the updated photos for the house
                        update.setHousePhotos(updatedPhotos);

                        // Save the changes to the house
                        HouseBean savedHouse = houseRepository.save(update);

                        // Update house references for the new photos (if any)
                        for (HousePhotoBean newPhoto : updatedPhotos) {
                            newPhoto.setHouseid(savedHouse.getHouseid());
                            housePhotoRepository.save(newPhoto);
                        }

                        // Set the updated photos for the house
                        update.setHousePhotos(updatedPhotos);
                    }
                    return houseRepository.save(update);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean exists(Integer id) {
        if (id != null) {
            return houseRepository.existsById(id);
        }
        return false;
    }

    public void deleteById(Integer houseId) {
        houseRepository.deleteById(houseId);
    }

    public void setNewHouse(HouseBean bean, String session) {
        Date date = DatetimeConverter.getCurrentDate();
        bean.setIsDeleted('0');
        bean.setUpdatedAt(date);
        bean.setCreatedAt(date);
        bean.setStatus("未綁定");
        bean.setNotes(null);
        System.out.println(bean);
        bean = houseRepository.save(bean);

        housePhotoService.getPhoto(session, bean.getHouseid());
    }
}