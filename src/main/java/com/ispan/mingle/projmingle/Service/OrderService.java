package com.ispan.mingle.projmingle.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.domain.HousePhotoBean;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.repository.HousePhotoRepository;
import com.ispan.mingle.projmingle.repository.VolunteerDetailRepository;
import com.ispan.mingle.projmingle.repository.WorkRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private VolunteerDetailRepository volunteerDetailRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private HousePhotoRepository housePhotoRepository;

    /** 透過會員id查詢會員資料 */
    public VolunteerDetailBean selectVolunteerDetail(String id) {
        VolunteerDetailBean detail = volunteerDetailRepository.findById(id).orElse(null);
        return detail;
    }

    /** 透過工作id查詢工作資料 */
    public WorkBean selectWorkDetail(Integer workid) {
        WorkBean detail = workRepository.findById(workid).orElse(null);
        return detail;
    }

    /** 透過工作id查詢關聯房間 */

    public List<HouseBean> selectWorkHouse(Integer workid) {
        List<HouseBean> list = workRepository.findHousesByWorkId(workid);
        return list;
    }

    /** 透過房屋id查詢房屋圖片 */
    public List<String> selectHouseImages(Integer photoid) {
        if (photoid != null) {

            // 使用 housePhotoRepository 找到對應的 HousePhotoBean 列表
            List<HousePhotoBean> housePhotoList = housePhotoRepository.findAllById(photoid);

            // 提取每個 HousePhotoBean 的圖片 URL 並存儲在一個列表中
            List<String> imageList = new ArrayList<>();
            for (HousePhotoBean housePhoto : housePhotoList) {
                String basePhoto = "data:imgage/jpeg;base64," + Base64.getEncoder().encodeToString(housePhoto.getPhoto());
                imageList.add(basePhoto);
            }
            return imageList;
        }
        return null;
    }
}
