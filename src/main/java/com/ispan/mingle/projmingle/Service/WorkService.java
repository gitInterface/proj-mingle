package com.ispan.mingle.projmingle.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.WorkBean;
import com.ispan.mingle.projmingle.repository.WorkRepository;

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
    private GoogleMapsGeocodingService geocodingService;

    // 工作排序[DEPRECATED]
    // public List<WorkBean> getHotWorks() {
    // Sort sort = Sort.by(Sort.Direction.DESC, "views");
    // return workRepository.findAll(sort);
    // }
    // public List<WorkBean> getLatestWorks() {
    // Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
    // return workRepository.findAll(sort);
    // }
    // public List<WorkBean> getOldestWorks() {
    // Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
    // return workRepository.findAll(sort);
    // }
    // public List<WorkBean> getDeadlineWorks() {
    // Sort sort = Sort.by(Sort.Direction.ASC, "EndDate");
    // return workRepository.findAll(sort);
    // }
    // public List<WorkBean> getWorksByRemainingSpotsAsc() {
    // List<WorkBean> works = workRepository.findAll();
    // works.sort(Comparator.comparingInt((WorkBean w) -> w.getMaxAttendance() -
    // w.getAttendance()));
    // return works;
    // }
    // public List<WorkBean> getWorksByRemainingSpotsDesc() {
    // List<WorkBean> works = workRepository.findAll();
    // works.sort(Comparator.comparingInt((WorkBean w) -> w.getMaxAttendance() -
    // w.getAttendance()).reversed());
    // return works;
    // }

    // Pageable神器
    public Page<WorkBean> getWorks(Pageable pageable, String direction, String property, Map<String, List<String>> filterMap) {
        // 定義排序規則
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sortSpecification = Sort.by(sortDirection, property);
        // 定義排序規則
        // Sort sortSpecification;
        // switch (sort) {
        //     case "hot":
        //         sortSpecification = Sort.by(Sort.Direction.DESC, "views");
        //         break;
        //     case "latest":
        //         sortSpecification = Sort.by(Sort.Direction.DESC, "createdAt");
        //         break;
        //     case "deadline":
        //         sortSpecification = Sort.by(Sort.Direction.ASC, "EndDate");
        //         break;
        //     case "attendanceAsc":
        //         sortSpecification = Sort.by(Sort.Direction.ASC, "attendance", "maxAttendance");
        //         break;
        //     case "attendanceDesc":
        //         sortSpecification = Sort.by(Sort.Direction.DESC, "attendance", "maxAttendance");
        //         break;
        //     default:
        //         sortSpecification = Sort.unsorted();
        // }

        // 將排序規則套用到分頁請求
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpecification);

        // 定義 Specification
        Specification<WorkBean> spec = new Specification<WorkBean>() {
            @Override
            public Predicate toPredicate(Root<WorkBean> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (filterMap.containsKey("worktype")) {
                    List<String> worktypeFilter = filterMap.get("worktype");
                    if(worktypeFilter != null && worktypeFilter.size() > 0){
                        predicates.add(root.get("worktype").in(worktypeFilter));
                    }
                }
                if (filterMap.containsKey("city")) {
                    List<String> cityFilter = filterMap.get("city");
                    predicates.add(root.get("city").in(cityFilter));
                }
                // ... add more predicates as needed ...
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };

        // 將 Specification 和 Pageable 套用到查詢[DEPRECATED]
        Page<WorkBean> worksPage = workRepository.findAll(spec, sortedPageable);


        // 根據帶入的 sort 參數來排序 (必須在資料庫查詢後才能排序的值)[DEPRECATED]
        // switch (sort) {
        // case "spotsAsc":
        // works.sort(Comparator.comparingInt((WorkBean w) -> w.getMaxAttendance() -
        // w.getAttendance()));
        // break;
        // case "spotsDesc":
        // works.sort(
        // Comparator.comparingInt((WorkBean w) -> w.getMaxAttendance() -
        // w.getAttendance()).reversed());
        // break;
        // default:
        // // 不需要進行排序
        // }

        // 回傳處理完成的結果：工作列表(WorkBean 物件)、Pageable 物件(包含分頁資訊及排序規則)、總筆數
        List<WorkBean> works = new ArrayList<>(worksPage.getContent());
        return new PageImpl<>(works, sortedPageable, worksPage.getTotalElements());
    }

    // 地址格式化
    public List<String> getFormattedAddresses() {
        List<WorkBean> workBeans = workRepository.findAll();
        return geocodingService.getFormattedAddresses(workBeans);
    }

}
