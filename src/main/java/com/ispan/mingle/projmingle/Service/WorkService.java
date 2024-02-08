package com.ispan.mingle.projmingle.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.mingle.projmingle.domain.HouseBean;
import com.ispan.mingle.projmingle.domain.HousePhotoBean;
import com.ispan.mingle.projmingle.domain.KeepWorkBean;
import com.ispan.mingle.projmingle.domain.VolunteerBean;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.domain.WorkHouseBean;
import com.ispan.mingle.projmingle.domain.WorkPhotoBean;
import com.ispan.mingle.projmingle.dto.WorkCreateDTO;
import com.ispan.mingle.projmingle.dto.WorkModifyDTO;
import com.ispan.mingle.projmingle.dto.WorkModifyHouseDTO;
import com.ispan.mingle.projmingle.dto.WorkModifyListDTO;
import com.ispan.mingle.projmingle.dto.WorkModifySubmitWorkDTO;
import com.ispan.mingle.projmingle.repository.HouseRepository;
import com.ispan.mingle.projmingle.repository.KeepWorkRepository;
import com.ispan.mingle.projmingle.repository.VolunteerRepository;
import com.ispan.mingle.projmingle.repository.WorkHouseRepository;
import com.ispan.mingle.projmingle.repository.WorkPhotoRepository;
import com.ispan.mingle.projmingle.repository.WorkRepository;
import com.ispan.mingle.projmingle.util.BaseUtil;
import com.ispan.mingle.projmingle.util.DatetimeConverter;
import com.ispan.mingle.projmingle.util.FileUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class WorkService {
    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WorkPhotoService workPhotoService;

    @Autowired
    private WorkPhotoRepository workPhotoRepository;

    @Autowired
    private KeepWorkRepository keepWorkRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private WorkHouseRepository workHouseRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private GoogleMapsGeocodingService geocodingService;

    @Autowired
    private WorkModifyDTO workModifyDTO;

    @Autowired
    private WorkModifyHouseDTO workModifyHouseDTO;

    private final ModelMapper modelMapper;

    public WorkService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /*
     * R
     */

    // 依據查詢條件獲取工作
    public Page<WorkBean> getWorks(Pageable pageable, String direction, String property,
            Map<String, ?> filterMap, String userID) {
        // 定義排序規則

        if (direction == null) {
            direction = "asc";
        }
        if (property == null) {
            property = "workID";
        }
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sortSpecification = Sort.by(sortDirection, property);

        // 將排序規則套用到分頁請求
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpecification);

        // 定義 Specification 篩選器
        Specification<WorkBean> spec = new Specification<WorkBean>() {
            @Override
            public Predicate toPredicate(Root<WorkBean> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // 排除 isDeleted 的工作
                if (filterMap.containsKey("hideDeleted")) {
                    Boolean hideDeleted = (Boolean) filterMap.get("hideDeleted");
                    if (hideDeleted != null && hideDeleted == true) {
                        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
                    }
                }
                // 僅拿取指定狀態(例如：已上架)的工作
                if (filterMap.containsKey("showOnShelfOnly")) {
                    Boolean showOnShelfOnly = (Boolean) filterMap.get("showOnShelfOnly");
                    if (showOnShelfOnly != null && showOnShelfOnly == true) {
                        predicates.add(criteriaBuilder.equal(root.get("isOnShelf"), true));
                    }
                }
                if (filterMap.containsKey("workStatus")) {
                    List<String> workStatusFilter = (List<String>) filterMap.get("workStatus");
                    if (workStatusFilter != null && workStatusFilter.size() > 0) {
                        predicates.add(root.get("status").in(workStatusFilter));
                    }
                }
                // 排除已過期的工作
                if (filterMap.containsKey("hideExpired")) {
                    Boolean hideExpired = (Boolean) filterMap.get("hideExpired");
                    if (hideExpired != null && hideExpired == true) {
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), new Date()));
                    }
                }

                // 參與人數：可排除名額已滿的工作
                if (filterMap.containsKey("hideFull")) {
                    Boolean hideFull = (Boolean) filterMap.get("hideFull");
                    if (hideFull != null && hideFull == true) {
                        predicates.add(criteriaBuilder.lessThan(root.get("attendance"), root.get("maxAttendance")));
                    }
                }

                // 工作類型：可不選、可複選
                if (filterMap.containsKey("worktype")) {
                    List<String> worktypeFilter = (List<String>) filterMap.get("worktype");
                    if (worktypeFilter != null && worktypeFilter.size() > 0) {
                        predicates.add(root.get("worktype").in(worktypeFilter));
                    }
                }
                // 縣市：可選所有縣市、整個區域、單個縣市
                if (filterMap.containsKey("city")) {
                    String cityFilter = (String) filterMap.get("city");
                    if (cityFilter.length() == 3) {
                        predicates.add(criteriaBuilder.equal(root.get("city"), cityFilter));
                    } else if (cityFilter.length() == 4) {
                        predicates.add(criteriaBuilder.equal(root.join("cityBean").get("area"), cityFilter));
                    }
                }

                // 工作日期：可選日期區間
                if (filterMap.containsKey("workPeriod")) {
                    List<String> workPeriod = (List<String>) filterMap.get("workPeriod");
                    if (workPeriod != null && workPeriod.size() > 0) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date startDate = formatter.parse(workPeriod.get(0));
                            Date endDate = formatter.parse(workPeriod.get(1));
                            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), startDate));
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), endDate));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                // 工作名稱：可用關鍵字模糊搜尋 (以空白鍵作為分隔，只要任一關鍵字符合就會顯示)
                if (filterMap.containsKey("keyword")) {
                    String keywordString = (String) filterMap.get("keyword");
                    if (keywordString != null) {
                        String[] keywords = keywordString.split("\\s+");
                        List<Predicate> keywordPredicates = new ArrayList<>();
                        for (String keyword : keywords) {
                            keywordPredicates.add(criteriaBuilder.like(root.get("name"), "%" + keyword + "%"));
                        }
                        predicates.add(criteriaBuilder.or(keywordPredicates.toArray(new Predicate[0])));
                    }
                }
                // 備選方案(所有關鍵字都需符合才會顯示)
                // if (filterMap.containsKey("keyword")) {
                // String keywordString = filterMap.get("keyword").get(0);
                // if (keywordString != null && !keywordString.isEmpty()) {
                // String[] keywords = keywordString.split("\\s+");
                // for (String keyword : keywords) {
                // predicates.add(criteriaBuilder.like(root.get("name"), "%" + keyword + "%"));
                // }
                // }
                // }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };

        // 將 Specification 和 Pageable 套用到查詢
        Page<WorkBean> worksPage = workRepository.findAll(spec, sortedPageable);

        // 回傳處理完成的結果：工作列表(WorkBean 物件)、Pageable 物件(包含分頁資訊及排序規則)、總筆數
        List<WorkBean> works = new ArrayList<>(worksPage.getContent());

        // 若有回傳userID，查詢KeepWork中user已收藏的工作
        if (userID != null) {
            VolunteerBean volunteer = volunteerRepository.findById(userID).orElse(null);
            if (volunteer != null) {
                List<KeepWorkBean> keptWorks = keepWorkRepository.findByVolunteer(volunteer);
                Set<WorkBean> keptWorkBeans = keptWorks.stream()
                        .map(KeepWorkBean::getWork)
                        .collect(Collectors.toSet());
                for (WorkBean work : works) {
                    if (keptWorkBeans.contains(work)) {
                        work.setKept(true);
                    }
                }
                if (filterMap.containsKey("showKeptWorkOnly")) {
                    Boolean showKeptWorkOnly = (Boolean) filterMap.get("showKeptWorkOnly");
                    if (showKeptWorkOnly != null && showKeptWorkOnly == true) {
                        works = works.stream()
                                .filter(WorkBean::isKept)
                                .collect(Collectors.toList());
                    }
                }
            }
        }
        // 工作咖啡豆照片沖洗館：一人限一張照片
        for (WorkBean work : works) {
            List<String> photosBase64 = work.getUndeletedWorkPhotoBeans().stream()
                    .limit(1)
                    .map(photo -> BaseUtil.byteToBase64(photo.getContentType(),
                            photo.getPhoto()))
                    .collect(Collectors.toList());
            work.setPhotosBase64(photosBase64);
        }

        return new PageImpl<>(works, sortedPageable, worksPage.getTotalElements());
    }

    // 依據某個workid獲取工作
    public WorkBean getWork(Integer workid) {
        WorkBean work = workRepository.findById(workid).orElse(null);
        if (work != null && !work.getIsDeleted()) {
            List<String> photosBase64 = work.getUndeletedWorkPhotoBeans().stream()
                    .map(photo -> BaseUtil.byteToBase64(photo.getContentType(),
                            photo.getPhoto()))
                    .collect(Collectors.toList());
            work.setPhotosBase64(photosBase64);
        }
        return work;
    }

    // 查詢待審核工作數量
    public Integer countPendingReview() {
        return workRepository.findPendingWorkCount();
    }

    // 查詢某個地址的所有work
    public List<WorkBean> getWorksByAddress(String address) {
        // 定義 Specification
        Specification<WorkBean> spec = new Specification<WorkBean>() {
            @Override
            public Predicate toPredicate(Root<WorkBean> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 檢查 WorkBean 的地址是否在提供的地址列表中
                Predicate addressPredicate = criteriaBuilder.equal(root.get("address"), address);
                // 檢查 WorkBean 是否為isDeleted
                Predicate notDeletedPredicate = criteriaBuilder.equal(root.get("isDeleted"), false);
                return criteriaBuilder.and(addressPredicate, notDeletedPredicate);
            }
        };

        // 使用 Specification 來查詢所有相符的 WorkBean
        List<WorkBean> works = workRepository.findAll(spec);

        // 轉換 WorkBean 列表為 workid 列表
        // List<Integer> workIds = works.stream()
        // .map(WorkBean::getWorkid)
        // .collect(Collectors.toList());

        return works;
    }

    // 地址格式化
    public List<String> getFormattedAddresses() {
        List<WorkBean> workBeans = workRepository.findAll();
        return geocodingService.getFormattedAddresses(workBeans);
    }

    public void setNewWork(WorkCreateDTO workDTO) {
        Date date = DatetimeConverter.getCurrentDate();
        Integer workID = 1;
        workDTO.setStatus("未上架");
        workDTO.setOnShelf(false);
        workDTO.setCreatedAt(date);
        // System.out.println(date);
        workDTO.setUpdatedAt(date);
        workDTO.setIsDeleted(false);
        // workDTO.setWorkID(workID);
        // workDTO.setMaxAttendance(6);
        workDTO.setViews(0);
        // workDTO.setFkLandlordID(2);//沒有land統一先2
        workDTO.setAttendance(0);// 不知道是什麼先0
        // System.out.println(workDTO);
        String session = workDTO.getSessionToken();
        WorkBean workEntity = modelMapper.map(workDTO, WorkBean.class);
        workEntity = workRepository.save(workEntity);
        // workRepository.save();
        // System.out.println(workEntity);
        // System.out.println("拿到的:" + session);
        workPhotoService.getPhoto(session, workEntity.getWorkid());
    }

    //// Update

    // 增加某workid的瀏覽量
    public void increaseViewCount(Integer workid) {
        Optional<WorkBean> optionalWork = workRepository.findById(workid);
        if (optionalWork.isPresent()) {
            WorkBean work = optionalWork.get();
            work.setViews(work.getViews() + 1);
            workRepository.save(work);
        } else {

        }
    }

    // (工作管理/修改初始渲染)workid查詢work, workPhoto
    public WorkModifyDTO showModify(Integer workid) {
        if (workid != null && workRepository.existsById(workid)) {
            WorkBean work = workRepository.findById(workid).get();
            BeanUtils.copyProperties(work, workModifyDTO);
            // 工作照片base64 (沒被刪除的)
            List<WorkPhotoBean> undeletedWorkPhotoBeans = work.getUndeletedWorkPhotoBeans();
            workModifyDTO.setPhotosBase64(undeletedWorkPhotoBeans.stream()
                    .map(bean -> BaseUtil.byteToBase64(bean.getContentType(), bean.getPhoto()))
                    .collect(Collectors.toList()));
            // (同上)照片id (使用者刪除照片的話，回傳id就好而不是整個base64)
            workModifyDTO.setPhotosID(
                    undeletedWorkPhotoBeans.stream().map(bean -> bean.getPhotoid()).collect(Collectors.toList()));

            return workModifyDTO;
        }
        return null;
    }

    // (工作管理/修改初始渲染)渲染房子相關，速度太慢了先拆開非同步請求
    public WorkModifyHouseDTO showModifyHouse(Integer workid) {
        if (workid != null && workRepository.existsById(workid)) {
            WorkBean work = workRepository.findById(workid).get();
            // Lord本身有的房(排除已經刪掉的；以及床位需>0)
            List<HouseBean> housesDetail = houseRepository
                    .findHousesWithNonDeletedAndExistBeds(work.getLandlordid());
            // 轉base64
            // 1.遍歷每個房，取出各自擁有的照片，另外建一個放置64的字串List(最後用)
            for (HouseBean house : housesDetail) {
                List<String> housePhotos64 = new ArrayList<String>();
                List<HousePhotoBean> housePhotos = house.getHousePhotos();
                // 2.若照片List不為null及空，遍歷所有照片
                if (housePhotos != null && !housePhotos.isEmpty()) {
                    for (HousePhotoBean photo : housePhotos) {
                        // 3.排除被刪除掉的照片，一一轉為64
                        if (photo.getIsDeleted() == '0') {
                            String photoBase64 = BaseUtil.byteToBase64(photo.getContentType(), photo.getPhoto());
                            housePhotos64.add(photoBase64);
                            // photo.setPhoto(null);
                        }
                    }
                    // 4.放回第一步建立的List
                    house.setPhotosBase64(housePhotos64);
                }
            }
            workModifyHouseDTO.setHouseDetail(housesDetail);
            // 綁定資訊(找出所有目前綁定的houseid)，不為空才set到DTO
            // System.out.println("workid=" + workid);
            List<Integer> bindingHousesID = workHouseRepository.findHouseIdsByWorkIdAndIsDeletedFalse(workid);
            // // System.out.println(bindingHousesID);
            // for (Integer id : bindingHousesID) {
            // System.out.println("id=" + id);
            // }
            if (bindingHousesID != null && !bindingHousesID.isEmpty()) {
                workModifyHouseDTO.setBindingHousesID(bindingHousesID);
            }
            return workModifyHouseDTO;
        }
        return null;
    }

    // (工作管理/提交基本資訊)
    public void workModifyWork(WorkModifySubmitWorkDTO requestWork, Integer workid) {
        if (workRepository.existsById(workid)) {
            WorkBean workBean = workRepository.findById(workid).get();
            BeanUtils.copyProperties(requestWork, workBean);
            // 小寫boolean型別在controller承接時轉換有點問題。因此DTO先大寫，然後手動set處理
            workBean.setOnShelf(requestWork.getIsOnShelf());
            workRepository.save(workBean);
        }
        // 處理舊照片
        Integer[] deletePhotoID = requestWork.getDeletePhotoID();
        if (deletePhotoID != null && deletePhotoID.length != 0) {
            for (Integer photoid : deletePhotoID) {
                WorkPhotoBean workPhotoBean = workPhotoRepository.findById(photoid).get();
                workPhotoBean.setIsDeleted(true);
                workPhotoRepository.save(workPhotoBean);
            }
        }
        // 處理綁定房
        Integer[] bindingChangeHouse = requestWork.getBindingChangeHouse();
        if (bindingChangeHouse != null && bindingChangeHouse.length != 0) {
            for (Integer houseid : bindingChangeHouse) {
                WorkHouseBean workHouseBean = workHouseRepository.findByWorkidAndHouseid(workid, houseid);
                if (workHouseBean != null) {
                    // 存在 -> 反轉true/false
                    workHouseBean.setDeleted(!workHouseBean.isDeleted());
                    workHouseRepository.save(workHouseBean);
                } else {
                    // 不存在 -> 新增並設false
                    WorkHouseBean houseBean = new WorkHouseBean();
                    houseBean.setWorkid(workid);
                    houseBean.setHouseid(houseid);
                    houseBean.setDeleted(false);
                    workHouseRepository.save(houseBean);
                }
            }
        }
    }

    // (工作管理/提交新照片)
    public void workModifyPhoto(List<MultipartFile> newList, Integer workid) {
        try {
            for (MultipartFile multipartFile : newList) {
                File file = FileUtil.convertMultipartFileToFile(multipartFile);
                WorkPhotoBean workPhotoBean = new WorkPhotoBean();
                workPhotoBean.setWorkid(workid);
                // 文件大小 (long -> int -> Integer)
                workPhotoBean.setPhotoSize((int) file.length());
                // 副檔名切割(ex. image/jpeg -> jpeg)
                String contentType = multipartFile.getContentType();
                String[] parts = contentType.split("/");
                workPhotoBean.setContentType(parts[1]);
                FileInputStream fis = new FileInputStream(file);
                workPhotoBean.setPhoto(fis.readAllBytes());
                workPhotoBean.setCreatedAt(new Date());
                workPhotoBean.setUpdatedAt(new Date());
                workPhotoBean.setIsDeleted(false);
                workPhotoRepository.save(workPhotoBean);

                fis.close();
                // 在处理完文件后，记得将文件删除，以释放资源
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // lordid找所有工作、各自照片 (都是未被刪除的)
    public List<WorkModifyListDTO> showWorkList(Integer lordid) {
        ArrayList<WorkModifyListDTO> result = new ArrayList<WorkModifyListDTO>();
        if (lordid != null) {
            List<WorkBean> works = workRepository.findByLandlordid(lordid);
            if (works != null) {
                if (works.size() != 0) {
                    // 建立一個DTO的List，最後return用
                    // 遍歷每個工作
                    for (WorkBean work : works) {
                        if (!work.getIsDeleted()) {
                            // 處理沒被刪除的工作，先copy到一個新的DTO
                            WorkModifyListDTO workModifyListDTO = new WorkModifyListDTO();
                            BeanUtils.copyProperties(work, workModifyListDTO);
                            // 該單一工作，尋找未刪除的照片，將其一一轉為base64後塞入List
                            ArrayList<String> photosBase64 = new ArrayList<String>();
                            List<WorkPhotoBean> workPhotos = work.getUndeletedWorkPhotoBeans();
                            if (workPhotos != null) {
                                if (workPhotos.size() != 0) {
                                    for (WorkPhotoBean workPhoto : workPhotos) {
                                        String photoBase64 = BaseUtil.byteToBase64(workPhoto.getContentType(),
                                                workPhoto.getPhoto());
                                        photosBase64.add(photoBase64);
                                    }
                                    workModifyListDTO.setPhotosBase64(photosBase64);
                                }
                            }
                            result.add(workModifyListDTO);
                        }
                    }
                }
            }
        }
        return result;
    }

    // 获取 newList 的方法，这里你需要根据你的实际情况实现
    public static List<MultipartFile> getNewList() {
        // 实现获取 newList 的逻辑，例如从请求中获取或者从其他地方获取
        return null;
    }

    /** 透過WorkID修改已報名人數 */
    public WorkBean updateAttendance(Integer workid, Integer attendance) {
        WorkBean work = workRepository.findById(workid).orElse(null);
        Integer original = work.getAttendance();
        work.setAttendance(original + attendance);
        ;
        return workRepository.save(work);
    }

}
