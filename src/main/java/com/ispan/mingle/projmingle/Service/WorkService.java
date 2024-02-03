package com.ispan.mingle.projmingle.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.KeepWorkBean;
import com.ispan.mingle.projmingle.domain.VolunteerBean;
import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.dto.WorkCreateDTO;
import com.ispan.mingle.projmingle.repository.KeepWorkRepository;
import com.ispan.mingle.projmingle.repository.VolunteerRepository;
import com.ispan.mingle.projmingle.repository.WorkRepository;
import com.ispan.mingle.projmingle.util.BaseUtil;
import com.ispan.mingle.projmingle.util.DatetimeConverter;

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
    private KeepWorkRepository keepWorkRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private GoogleMapsGeocodingService geocodingService;

    private final ModelMapper modelMapper;

    public WorkService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // 依據查詢條件獲取工作
    public Page<WorkBean> getWorks(Pageable pageable, String direction, String property,
            Map<String, ?> filterMap, String userID) {
        // 定義排序規則

        if (direction == null) {direction = "asc";}
        if (property == null) {property = "workID";}
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
                    Boolean hideDeleted = (Boolean) filterMap.get("hideFull");
                    if (hideDeleted != null && hideDeleted == true) {
                        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
                    }
                }
                // 僅拿取已上架的工作
                if (filterMap.containsKey("showOnShelfOnly")) {
                    Boolean showOnShelfOnly = (Boolean) filterMap.get("showOnShelfOnly");
                    if (showOnShelfOnly != null && showOnShelfOnly == true) {
                        predicates.add(criteriaBuilder.equal(root.get("status"), "已上架"));
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
                if (filterMap.containsKey("workperiod")) {
                    List<String> workperiod = (List<String>) filterMap.get("workperiod");
                    if (workperiod != null && workperiod.size() > 0) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date startDate = formatter.parse(workperiod.get(0));
                            Date endDate = formatter.parse(workperiod.get(1));
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
        // 檢查 WorkBean 是否為isDeleted
        if (work != null && !work.getIsDeleted()) {
            List<String> photosBase64 = work.getUndeletedWorkPhotoBeans().stream()
                    .map(photo -> BaseUtil.byteToBase64(photo.getContentType(),
                            photo.getPhoto()))
                    .collect(Collectors.toList());
            work.setPhotosBase64(photosBase64);
        }
        return work;
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
}
