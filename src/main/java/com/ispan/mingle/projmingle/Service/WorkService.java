package com.ispan.mingle.projmingle.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.dto.WorkCreateDTO;
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

    // @Autowired
    // private CityRepository cityRepository;

    @Autowired
    private GoogleMapsGeocodingService geocodingService;

    private final ModelMapper modelMapper;

    public WorkService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // 依據查詢條件獲取工作
    public Page<WorkBean> getWorks(Pageable pageable, String direction, String property,
            Map<String, List<String>> filterMap) {
        // 定義排序規則
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sortSpecification = Sort.by(sortDirection, property);

        // 將排序規則套用到分頁請求
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpecification);

        // 定義 Specification
        Specification<WorkBean> spec = new Specification<WorkBean>() {
            @Override
            public Predicate toPredicate(Root<WorkBean> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // 工作類型：可不選、可複選
                if (filterMap.containsKey("worktype")) {
                    List<String> worktypeFilter = filterMap.get("worktype");
                    if (worktypeFilter != null && worktypeFilter.size() > 0) {
                        predicates.add(root.get("worktype").in(worktypeFilter));
                    }
                }
                // 縣市：可選所有縣市、整個區域、單個縣市
                if (filterMap.containsKey("city")) {
                    String cityFilter = filterMap.get("city").get(0);
                    if (cityFilter.length() == 3) {
                        predicates.add(criteriaBuilder.equal(root.get("city"), cityFilter));
                    } else if (cityFilter.length() == 4) {
                        predicates.add(criteriaBuilder.equal(root.join("cityBean").get("area"), cityFilter));
                        // // 從 City 資料表中查詢符合該 area 的所有 city
                        // List<CityBean> cities = cityRepository.findByArea(cityFilter);
                        // // 從 cities 中提取所有的 city
                        // List<String> cityNames = cities.stream()
                        // .map(CityBean::getCity)
                        // .collect(Collectors.toList());
                        // // 使用這些 city 來查詢 Work
                        // if (!cityNames.isEmpty()) {
                        // predicates.add(root.get("city").in(cityNames));
                        // }
                    }
                }

                // 工作名稱：關鍵字模糊搜尋 (以空白鍵作為分隔，只要任一關鍵字符合就會顯示)
                if (filterMap.containsKey("keyword")) {
                    String keywordString = filterMap.get("keyword").get(0);
                    if (keywordString != null && !keywordString.isEmpty()) {
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

        // 工作咖啡豆照片沖洗館
        for (WorkBean work : works) {
            List<String> photosBase64 = work.getWorkPhotoBeans().stream()
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
        if (work != null) {
            List<String> photosBase64 = work.getWorkPhotoBeans().stream()
                    .map(photo -> BaseUtil.byteToBase64(photo.getContentType(),
                            photo.getPhoto()))
                    .collect(Collectors.toList());
            work.setPhotosBase64(photosBase64);
        }
        return work;
    }

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

    // 查詢某個地址的所有work
    public List<WorkBean> getWorksByAddress(String address) {
        // 定義 Specification
        Specification<WorkBean> spec = new Specification<WorkBean>() {
            @Override
            public Predicate toPredicate(Root<WorkBean> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 檢查 WorkBean 的地址是否在提供的地址列表中
                return criteriaBuilder.equal(root.get("address"), address);
            }
        };

        // 使用 Specification 來查詢所有匹配的 WorkBean
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
        System.out.println(date);
        workDTO.setUpdatedAt(date);
        workDTO.setIsDeleted(false);
        // workDTO.setWorkID(workID);
        // workDTO.setMaxAttendance(6);
        workDTO.setViews(0);
        // workDTO.setFkLandlordID(2);//沒有land統一先2
        workDTO.setAttendance(0);// 不知道是什麼先0
        System.out.println(workDTO);
        String session = workDTO.getSessionToken();
        WorkBean workEntity = modelMapper.map(workDTO, WorkBean.class);
        workEntity = workRepository.save(workEntity);
        // workRepository.save();
        // System.out.println(workEntity);
        // System.out.println("拿到的:" + session);
        workPhotoService.getPhoto(session, workEntity.getWorkid());
    }
}
