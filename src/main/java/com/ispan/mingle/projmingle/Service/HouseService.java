package com.ispan.mingle.projmingle.Service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.repository.HouseRepository;
import com.ispan.mingle.projmingle.util.DatetimeConverter;

@Service
@Transactional
public class HouseService {
    @Autowired
    private HouseRepository houseRepository;

    public List<HouseBean> findAllHouses() {
        return houseRepository.findAll();
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
            Character  hasTV = obj.isNull("hasTV") ? null : obj.getString("hasTV").charAt(0);
            Character  hasKitchen = obj.isNull("hasKitchen") ? null : obj.getString("hasKitchen").charAt(0);
            Character  hasLaundry = obj.isNull("hasLaundry") ? null : obj.getString("hasLaundry").charAt(0);
            Character  hasParkingLot = obj.isNull("hasParkingLot") ? null : obj.getString("hasParkingLot").charAt(0);
            Character  hasAirconditioner = obj.isNull("hasAirconditioner") ? null
                    : obj.getString("hasAirconditioner").charAt(0);
            Character  hasPersonalSpace = obj.isNull("hasPersonalSpace") ? null
                    : obj.getString("hasPersonalSpace").charAt(0);
            Character  hasPool = obj.isNull("hasPool") ? null : obj.getString("hasPool").charAt(0);
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

}