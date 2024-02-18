package com.ispan.mingle.projmingle.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.AccommodatorBean;
import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.domain.HousePhotoBean;
import com.ispan.mingle.projmingle.domain.OrderBean;
import com.ispan.mingle.projmingle.domain.OrderWorkHouseBean;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.domain.WorkHouseBean;
import com.ispan.mingle.projmingle.dto.LandlordOrderDTO;
import com.ispan.mingle.projmingle.dto.ReviewDTO;
import com.ispan.mingle.projmingle.dto.UserOrderDTO;
import com.ispan.mingle.projmingle.repository.AccommodatorRepository;
import com.ispan.mingle.projmingle.repository.HousePhotoRepository;
import com.ispan.mingle.projmingle.repository.LandlordRepository;
import com.ispan.mingle.projmingle.repository.OrderRepository;
import com.ispan.mingle.projmingle.repository.OrderWorkHouseRepository;
import com.ispan.mingle.projmingle.repository.VolunteerDetailRepository;
import com.ispan.mingle.projmingle.repository.WorkHouseRepository;
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

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderWorkHouseRepository orderWorkHouseRepository;

    @Autowired
    private WorkHouseRepository workHouseRepository;

    @Autowired
    private AccommodatorRepository accommodatorRepository;

    @Autowired
    private LandlordRepository landlordRepository;

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
    public List<String> selectHouseImages(Integer houseid) {
        if (houseid != null) {

            // 使用 housePhotoRepository 找到對應的 HousePhotoBean 列表
            List<HousePhotoBean> housePhotoList = housePhotoRepository.findAllByHouseId(houseid);
            // 提取每個 HousePhotoBean 的圖片 URL 並存儲在一個列表中
            List<String> imageList = new ArrayList<>();
            for (HousePhotoBean housePhoto : housePhotoList) {
                String basePhoto = "data:image/" + housePhoto.getContentType() + ";base64,"
                        + Base64.getEncoder().encodeToString(housePhoto.getPhoto());
                imageList.add(basePhoto);
            }
            return imageList;
        }
        return null;
    }

    /** 創建Order */
    public OrderBean createOrder(OrderBean bean) {
        OrderBean order = orderRepository.save(bean);
        return order;
    }

    /** 創建 Order與 WorkHouse 關聯 */
    public OrderWorkHouseBean createOrderWorkHouse(OrderWorkHouseBean bean) {
        OrderWorkHouseBean order = orderWorkHouseRepository.save(bean);
        return order;
    }

    /** 透過房間id查詢關聯工作房間的詳細資料 */
    public List<WorkHouseBean> selectWorkHouseDetail(Integer houseid) {
        List<WorkHouseBean> list = workHouseRepository.findAllByHouseId(houseid);
        return list;

    }

    /** 創建住宿者資料 */
    public AccommodatorBean createAccommodator(AccommodatorBean bean) {
        AccommodatorBean order = accommodatorRepository.save(bean);
        return order;
    }

    /** 用訂單orderid找會員詳細資料 */
    public VolunteerDetailBean getVolunteerDetailByOrderId(Integer orderid) {
        VolunteerDetailBean volunteerDetail = orderRepository.findVolunteerDetailByOrderId(orderid);
        return volunteerDetail;
    }

    /** 透過orderid找訂單 */
    public OrderBean getOrderDetailByOrderId(Integer orderid) {
        OrderBean order = orderRepository.findById(orderid).orElse(null);
        return order;
    }

    /** 用訂單id找工作 */
    public WorkBean findWorkBeanByOrderId(Integer orderid) {
        WorkBean work = orderRepository.findWorkBeanByOrderId(orderid);
        return work;
    }

    /** 用訂單id找房間 */
    public List<HouseBean> findHouseBeanByOrderId(Integer orderid) {
        List<HouseBean> house = orderRepository.findHouseBeanByOrderId(orderid);
        return house;
    }

    /** 用訂單id找ReviewDTO */
    public ReviewDTO findReviewDTOByOrderId(Integer orderid) {
        VolunteerDetailBean volunteerDetail = orderRepository.findVolunteerDetailByOrderId(orderid);
        OrderBean order = orderRepository.findById(orderid).orElse(null);
        WorkBean work = orderRepository.findWorkBeanByOrderId(orderid);
        List<HouseBean> house = orderRepository.findHouseBeanByOrderId(orderid);

        ReviewDTO review = new ReviewDTO();

        review.setUserid(volunteerDetail.getUserid());
        review.setUsername(volunteerDetail.getName());
        String basePhoto = "data:image/" + volunteerDetail.getPhotoType() + ";base64,"
                + Base64.getEncoder().encodeToString(volunteerDetail.getImage());
        review.setImage(basePhoto);
        review.setPhotoType(volunteerDetail.getPhotoType());
        review.setCountry(volunteerDetail.getCountry());
        review.setWorkid(work.getWorkid());
        review.setWorktype(work.getWorktype());
        review.setWorkName(work.getName());
        review.setCity(work.getCity());

        Date endDate = work.getEndDate();
        Date startDate = work.getStartDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        review.setStartDate(dateFormat.format(startDate));
        review.setEndDate(dateFormat.format(endDate));

        long differenceInMillis = endDate.getTime() - startDate.getTime();
        long daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
        review.setDays(daysDifference);

        List<Integer> houseidList = new ArrayList<>();
        List<String> houseNameList = new ArrayList<>();
        List<String> houseTypeList = new ArrayList<>();

        for (HouseBean houseBean : house) {

            houseidList.add(houseBean.getHouseid());
            houseNameList.add(houseBean.getName());
            houseTypeList.add(houseBean.getHouseType());

        }
        review.setHouseid(houseidList);
        review.setHouseName(houseNameList);
        review.setHouseType(houseTypeList);

        review.setOrderid(order.getOrderid());
        review.setNumbers(order.getNumbers());

        return review;

    }

    public List<LandlordOrderDTO> getAllOrderByLoardId(Integer id) {
        List<LandlordOrderDTO> order = landlordRepository.findAllOrderByLandlordID(id);
        Map<Integer, LandlordOrderDTO> mergedOrders = new LinkedHashMap<>();
        for (LandlordOrderDTO orderDTO : order) {
            int orderId = orderDTO.getOrder().getOrderid();
            if (mergedOrders.containsKey(orderId)) {
                LandlordOrderDTO existingDTO = mergedOrders.get(orderId);

                String houseName = existingDTO.getHouseName() != null
                        ? existingDTO.getHouseName() + "," + "\n" + orderDTO.getHouseName()
                        : orderDTO.getHouseName();
                existingDTO.setHouseName(houseName);
            } else {
                mergedOrders.put(orderId, orderDTO);
            }
        }

        return new ArrayList<>(mergedOrders.values());
    }

    /** 用會員id找UserOrderDTO */
    public List<UserOrderDTO> getAllOrderByUserId(String id) {
        List<UserOrderDTO> order = orderRepository.findWorkDetailAndPhotoByUserid(id);
        Map<Integer, UserOrderDTO> mergedOrders = new LinkedHashMap<>();
        for (UserOrderDTO orderDTO : order) {
            int orderId = orderDTO.getOrder().getOrderid();
            if (mergedOrders.containsKey(orderId)) {
                UserOrderDTO existingDTO = mergedOrders.get(orderId);

                String houseName = existingDTO.getHouseName() != null
                        ? existingDTO.getHouseName() + "," + "\n" + orderDTO.getHouseName()
                        : orderDTO.getHouseName();
                existingDTO.setHouseName(houseName);
            } else {
                mergedOrders.put(orderId, orderDTO);
            }
        }

        return new ArrayList<>(mergedOrders.values());
    }

    public void setOrderStatus(Integer id, String status, boolean cancelled) {
        landlordRepository.setOrderStatus(id, status, cancelled);
    }

    public List<LandlordOrderDTO> getOrderByLoardId(Integer id) {
        List<LandlordOrderDTO> dt = landlordRepository.findByOrderid(id);
        System.out.println(dt);
        return dt;
    }
}
